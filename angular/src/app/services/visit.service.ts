import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse } from '@angular/common/http';
import { IVisit } from '../models/Visit';
import { Observable } from 'rxjs';
import { ICluster } from '../models/Cluster';
import { VisitViewModel } from '../models/VisitViewModel';

@Injectable({
  providedIn: 'root'
})
export class VisitService {

  private readonly HEADERS = new HttpHeaders({ 'Content-Type':  'application/json' });
  private http: HttpClient;

  private readonly clusterUrl = "http://localhost:8080/api/cluster"
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

  createVisit(visitVM: VisitViewModel): Observable<HttpResponse<IVisit>> {
    let response$ = this.http.post<IVisit>(this.visitUrl, visitVM, { headers: this.HEADERS, observe: 'response'});
    return response$;
  }


}
