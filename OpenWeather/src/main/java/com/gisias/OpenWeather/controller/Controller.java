/**
 * 
 */
package com.gisias.OpenWeather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gisias.OpenWeather.Filter.TempFilter;
import com.gisias.OpenWeather.Filter.IndexTempFilter;
import com.gisias.OpenWeather.service.DataBaseImpl;
import com.gisias.OpenWeather.service.Parser;
import com.gisias.OpenWeather.service.StatsFilterImpl;

/**
 * Controller dell'applicativo OpenWeather
 * 
 * @author AndreaIasenzaniro
 * @author CarloGissi
 *
 */
@RestController
public class Controller {

	@Autowired
	DataBaseImpl databaseimpl;
	@Autowired
	StatsFilterImpl statsfilter;
	
	/**
	 * Rotta che consente di interpretare il tipo dati forniti come risposta dal programma
	 * 
	 * @return oggetti di tipo MetaData in formato Json
	 */
	@GetMapping("/metadata")
	public ResponseEntity<Object> getMetaData(){
		return new ResponseEntity<>(databaseimpl.parsMetaData(databaseimpl.getMetaData()), HttpStatus.OK);
	}
	/**
	 * Rotta che consente di visualizzare previsioni attuali di una città
	 * 
	 * @param name nome della città da ricercare
	 * @return Stringa dell'oggetto Weather in formato Json
	 */
	@GetMapping("/current")
	public ResponseEntity<Object> currentParser(@RequestParam(value="city") String city){
		return new ResponseEntity<>(Parser.currentParser(city), HttpStatus.OK);
	}
	/**
	 * Rotta che permette di ottenere statistiche filtrate per durata, di una città scelta
	 * 
	 * @param filter oggetto di tipo filter che contiene città e intervallo di ricerca
	 * @return Stringa Json con i dati relativi alla temperatura massima, minima, media e varianza
	 * @throws Exception
	 */
	@PostMapping("/currentstats")
	public String getTempStats(@RequestBody TempFilter filter) throws Exception{
		return statsfilter.getTempStats(filter);
	}
	/**
	 * Rotta che restituisce previsioni orarie storiche di una data città, in un arco temporale definito dall'utente
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/currentfilter")
	public String getTempFilter(@RequestBody TempFilter filter) throws Exception{
		return statsfilter.getTempFilter(filter);
	}
	/**
	 * Rotta che consente di effettuare 
	 * 
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/index")
	public String getIndexFilter(@RequestBody IndexTempFilter filter) throws Exception{
		return statsfilter.getIndexFilter(filter);
	}
}
