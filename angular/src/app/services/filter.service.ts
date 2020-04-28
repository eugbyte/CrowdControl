import { Injectable } from '@angular/core';
import { IShop } from '../models/shop';
import { PageEvent } from '@angular/material/paginator';
import { IVisit } from '../models/Visit';
import { ICluster } from '../models/Cluster';

@Injectable({
  providedIn: 'root'
})
export class FilterService {

  constructor() { }

  filterShopsBySearchText(shops: IShop[], searchText: string): IShop[] {
    searchText = searchText.toLowerCase().replace(/\s/g,'').toLowerCase();
    let resultShops: IShop[] = shops.filter(shop => {
      let { shopId, name, visits } = shop;
      let fullDescription: string = shopId + name;
      fullDescription = fullDescription.replace(/\s/g,'').toLowerCase();
      return (fullDescription.includes(searchText));
    });
    return resultShops;
  }

  filterClustersBySearchText(clusters: ICluster[], searchText: string): ICluster[] {
    console.log("in filter");
    searchText = searchText.toLowerCase().replace(/\s/g,'').toLowerCase();
    let resultCluters: ICluster[] = clusters.filter(cluster => {
      let { dateTimeIn, dateTimeOut, visits } = cluster;
      let fullDescription: string = dateTimeIn.toString() + dateTimeOut.toString();
      console.log(fullDescription);
      visits.forEach(visit => {
        let { dateTimeIn, dateTimeOut, visitor } = visit;
        fullDescription += dateTimeIn.toString() + dateTimeOut.toString() + visitor.name + visitor.nric;
      });
      fullDescription = fullDescription.replace(/\s/g,'').toLowerCase();
      return (fullDescription.includes(searchText));
    });
    return resultCluters;

  }

  filterVisitsByDates(visits: IVisit[], startDate: Date, endDate: Date): IVisit[] {
    let resultVisits = visits
      .filter(visit => (startDate && visit.dateTimeIn) ? visit.dateTimeIn >= startDate : true)
      .filter(visit => (endDate && visit.dateTimeOut) ? visit.dateTimeOut <= endDate : true);
      return resultVisits;
  }

  filterElementsToDisplayPerPage(elements: any[], pageSize: number, event?: PageEvent): any[] {

    //On the page initialization when the user has yet to click on the paginator, start filtering anyway
    if (event == undefined || event == null) {
      let resultElements: any[] = elements.slice(0, pageSize);
      return resultElements;
    }

    let currentPageIndex: number = event.pageIndex;

    let startIndex: number = currentPageIndex * pageSize;
    let endIndex: number = startIndex + pageSize;

    console.log(startIndex, endIndex);

    //if the user presses the backbutton
    if (startIndex > endIndex) {
      [startIndex, endIndex] = [endIndex, startIndex];
      if (startIndex - pageSize >= 0)
        [startIndex, endIndex] = [startIndex - pageSize, endIndex - pageSize];
    }
    let resultElements: any[] = elements.slice(startIndex, endIndex);

    console.log(resultElements);
    return resultElements;

  }
}
