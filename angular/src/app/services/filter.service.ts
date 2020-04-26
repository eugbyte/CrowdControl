import { Injectable } from '@angular/core';
import { IShop } from '../models/shop';
import { PageEvent } from '@angular/material/paginator';

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
