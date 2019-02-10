package com.example.polls.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String converteData(java.util.Date dtData){
		
		if (dtData != null) {
			   SimpleDateFormat formatBra;   
			   
			   DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			   formatBra = new SimpleDateFormat("yyyy/MM/dd");
			   try {
			        Date date1 = df.parse(dtData.toString());
			        
			        String output1 = formatBra.format(date1); //


			      return output1;
			   } catch (ParseException Ex) {
			      return "Erro na conversão da data";
			   }
		}
		return null;
	}
}
