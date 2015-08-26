package db;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.params.CoreConnectionPNames;

import model.City;
import model.County;
import model.Province;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyWeatherDB {

	// ���ݿ���
	public static final String DB_NAME = "my_weather";

	// ���ݿ�汾
	public static final int VERSION = 1;

	private static MyWeatherDB myWeatherDB;

	private SQLiteDatabase db;

	private MyWeatherDB(Context context) {
		MyWeatherOpenHelper dbhHelper = new MyWeatherOpenHelper(context,
				DB_NAME, null, VERSION);
		db = dbhHelper.getWritableDatabase();
	}
	
	public synchronized static MyWeatherDB getInstance(Context context){
		if (myWeatherDB == null) {
			myWeatherDB = new MyWeatherDB(context);
		}
		return myWeatherDB;
	}
	
	//save province
	public void saveProvince(Province province){
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	//read all province infomation in country from database
	public List<Province> loadProvinces(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	//��Cityʵ���洢�����ݿ�
	public void saveCity(City city){
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	
	//�����ݿ��ȡĳʡ�����еĳ�����Ϣ
	public List<City> loadCities(int provinceId){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				list.add(city);
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	public void saveCounty(County county){
		if (county != null) {
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}
	
	public List<County> loadCounties(int cityId){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				County county = new County();
				county.setCityId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cityId);
				list.add(county);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
