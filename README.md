# WeatherInfoForPincode

## Requirements

You need to provide a single REST API for weather information for a particular day and a Pincode.

Input
pincode: 411014
for_date: 2020-10-15

Weather information for Pincode
Save this information in DB(RDBMS)

1. Please save pincode lat, long separately
2. Also the weather information for the particular pincode
3. Next time we call the API then based on the information saved, API calls should be optimized

Ref: <https://openweathermap.org/current>

Optimized for API calls
● Pincode to lat long(Google Maps, Openweather Geocoding API)
● Lat long to Weather information (OpenWeather API)

Things to take care of:

1. Only REST API - No UI
2. Testable by Postman/Swagger - No UI
3. Will prefer code structured in proper fashion
4. Will prefer Testcases (TDD)
