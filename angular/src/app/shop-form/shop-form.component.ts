import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { Shop, IShop } from '../models/shop';
import { ShopService } from '../services/shop.service';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Route } from '@angular/compiler/src/core';

@Component({
  selector: 'app-shop-form',
  templateUrl: './shop-form.component.html',
  styleUrls: ['./shop-form.component.css']
})
export class ShopFormComponent implements OnInit {

  private readonly fb: FormBuilder;
  private readonly shopService: ShopService;
  private router: Router;
  private route: ActivatedRoute;

  shopForm: FormGroup;

  responseMessages: string[] = [];

  constructor(fb: FormBuilder, shopService: ShopService, router: Router, route: ActivatedRoute) {
    this.fb = fb;
    this.shopService = shopService;
    this.router = router;
    this.route = route;
   }

  ngOnInit(): void {
    let shopId: number = +this.route.snapshot.paramMap.get('id') ?? 0;
    console.log(shopId);
    this.shopForm = this.fb.group({
      shopId: [shopId],
      name: ['']
    });
    if (shopId)
      this.getShopToUpdate(shopId);
  }

  get name(): FormControl { return this.shopForm.get('name') as FormControl; }
  get shopId(): FormControl { return this.shopForm.get('shopId') as FormControl; }

  onSubmit() {
    let name: string = this.name.value;
    let shop: IShop = new Shop();
    shop.name = name;
    shop.shopId = this.shopId.value;
    shop.visits = null;
    if (this.shopId.value === 0) {
     this.createShop(shop);
    } else {
      console.log(shop);
      this.updateShop(shop);
    }
    
  }

  createShop(shop: IShop) {
    this.shopService.createShop(shop)
    .subscribe((res: HttpResponse<IShop>) => console.log(res),
      error => console.log(error),
      () => this.router.navigate(['shops', 'view']) );
  }

  getShopToUpdate(shopId: number): void {
    if (!shopId)
      return;
    this.shopService.getShop(shopId)
      .subscribe(shop => {
        this.name.setValue(shop.name);
        this.shopId.setValue(shop.shopId);
      },
        error => console.log(error));
  }

  updateShop(shop: IShop): void {
    this.shopService.updateShop(shop).subscribe(response => {
      console.log(response);
      let responseMessage: string = `shop ${response.body.shopId} updated!`;
      this.responseMessages.push(responseMessage);      
    },
      (error: HttpErrorResponse) => {
        console.log(error);
        this.responseMessages.push(error.error.toString());
      })
  }


}
