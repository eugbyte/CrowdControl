import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { Shop, IShop } from '../models/shop';
import { ShopService } from '../services/shop.service';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-shop-form',
  templateUrl: './shop-form.component.html',
  styleUrls: ['./shop-form.component.css']
})
export class ShopFormComponent implements OnInit {

  private readonly fb: FormBuilder;
  private readonly shopService: ShopService;
  private router: Router;

  shopForm: FormGroup;

  constructor(fb: FormBuilder, shopService: ShopService, router: Router) {
    this.fb = fb;
    this.shopService = shopService;
    this.router = router;
   }

  ngOnInit(): void {
    this.shopForm = this.fb.group({
      name: ['']
    });
  }

  get name(): FormControl { return this.shopForm.get('name') as FormControl; }

  onSubmit() {
    let name: string = this.name.value;
    let shop: IShop = new Shop();
    shop.name = name;
    this.shopService.createShop(shop)
      .subscribe((res: HttpResponse<IShop>) => console.log(res),
        error => console.log(error),
        () => this.router.navigate(['shops', 'view']) );
  }


}
