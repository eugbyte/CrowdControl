import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IShop } from '../models/shop';

@Injectable({
  providedIn: 'root'
})
export class ShopService {

  HEADERS = new HttpHeaders({ 'Content-Type':  'application/json' });
  http: HttpClient;

  private readonly shopUrl = "http://localhost:8080/api/shop";

  constructor(http: HttpClient) {
    this.http = http;
   }

   getShops(): Observable<IShop[]> {
     let shops$ = this.http.get<IShop[]>(this.shopUrl, { headers : this.HEADERS });
     return shops$;
   }

   createShop(shop: IShop): Observable<HttpResponse<IShop>> {
     let response$ = this.http.post<IShop>(this.shopUrl, shop, { headers : this.HEADERS, observe: 'response' });
     return response$;
   }
}
