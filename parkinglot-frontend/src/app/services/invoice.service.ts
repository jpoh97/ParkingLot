import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {
  private url = 'http://localhost:8080/api/v1/invoice';

  constructor(private http: HttpClient) { }

  getAll() {  
    return this.http.get(this.url+"/history?sort=entryDate,desc");
  }

  getOpenInvoices() {  
    return this.http.get(this.url);
  }
}
