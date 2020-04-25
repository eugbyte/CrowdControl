import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { IVisit } from '../models/Visit';
import { Observable } from 'rxjs';
import { ICluster } from '../models/Cluster';

@Injectable({
  providedIn: 'root'
})
export class VisitService {

  private readonly HEADERS = new HttpHeaders({ 'Content-Type':  'application/json' });
  private http: HttpClient;

  private readonly clusterUrl = "http://localhost:8080/api/visit/cluster"
  private readonly visitUrl = "http://localhost:8080/api/visit";
  
  constructor(http: HttpClient) { 
    this.http = http;
  }

  getAllVisitsOfShop(shopId: number): Observable<IVisit[]> {
    let url: string = this.visitUrl + "/shop/" + shopId;
    let response$ = this.http.get<IVisit[]>(url, { headers: this.HEADERS });
    return response$;
  }

  getClustersOfShop(shopId: number): Observable<ICluster[]> {
    let url = this.clusterUrl + '/' + shopId;
    let response$ = this.http.get<ICluster[]>(url, { headers: this.HEADERS });
    return response$;

  }


}
