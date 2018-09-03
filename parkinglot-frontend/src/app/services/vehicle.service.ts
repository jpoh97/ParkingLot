import { Vehicle } from './../models/vehicle.model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, RequestOptions, Headers } from '@angular/http';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {
  private url = 'http://localhost:8080/api/v1/vehicle';

  constructor(private httpClient: HttpClient, private http: Http) { }

  getAll() {  
    return this.httpClient.get(this.url);
  }

  checkOut(vehicle: string) {  
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
         
    return this.httpClient.put(this.url+"/"+vehicle, options);
  }

  checkIn(vehicle: Vehicle) {  
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    
    return this.http.post(this.url, JSON.stringify(vehicle), options);
  }
}
