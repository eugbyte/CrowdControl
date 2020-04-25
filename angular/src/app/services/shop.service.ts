import { Injectable, ɵSWITCH_COMPILE_DIRECTIVE__POST_R3__ } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IShop } from '../models/shop';

@Injectable({
  providedIn: 'root'
})
export class ShopService {

  HEADERS = new HttpHeaders({ 'Content-Type':  'application/json' });
  private http: HttpClient;

  private readonly shopUrl = "http://localhost:8080/api/shop";

  constructor(http: HttpClient) {
    this.http = http;
   }

   getShops(): Observable<IShop[]> {
     let shops$ = this.http.get<IShop[]>(this.shopUrl, { headers : this.HEADERS });
     return shops$;
   }

   getShop(shopId: number): Observable<IShop> {
     let url = this.shopUrl + '/' + shopId;
     let shop$ = this.http.get<IShop>(url, { headers: this.HEADERS });
     return shop$;
   }

   createShop(shop: IShop): Observable<HttpResponse<IShop>> {
     let response$ = this.http.post<IShop>(this.shopUrl, shop, { headers : this.HEADERS, observe: 'response' });
     return response$;
   }

   updateShop(shop: IShop): Observable<HttpResponse<IShop>> {
    let response$ = this.http.put<IShop>(this.shopUrl, shop, { headers : this.HEADERS, observe: 'response' });
    return response$;
   }

   deleteShop(shopId: number): Observable<HttpResponse<any>> {
     let url = this.shopUrl + '/' + shopId;
     let response$ = this.http.delete<any>(url, { headers : this.HEADERS, observe: 'response' });
     return response$;
   }
}
