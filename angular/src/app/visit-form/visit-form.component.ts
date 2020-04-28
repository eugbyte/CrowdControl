import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { IShop, Shop } from '../models/shop';
import { ShopService } from '../services/shop.service';
import * as lodash from "lodash";
import { Visit, IVisit } from '../models/Visit';
import { Visitor, IVisitor } from '../models/Visitor';
import { VisitService } from '../services/visit.service';
import { HttpErrorResponse } from '@angular/common/http';
import { VisitViewModel } from '../models/VisitViewModel';

@Component({
  selector: 'app-visit-form',
  templateUrl: './visit-form.component.html',
  styleUrls: ['./visit-form.component.css']
})
export class VisitFormComponent implements OnInit {

  visitForm: FormGroup;

  private SHOPS: IShop[];
  shopsToDisplay: IShop[];

  private fb: FormBuilder;  
  private shopService: ShopService;
  private visistService: VisitService;

  responseMessages: string[] = [];

  currentDateTime: string = new Date().toDateString() + " " + new Date().toLocaleTimeString();

  constructor(fb: FormBuilder, shopService: ShopService,visitService: VisitService) { 
    this.fb = fb;
    this.shopService = shopService;
    this.visistService = visitService;
  }

  ngOnInit(): void {
    this.visitForm = this.fb.group({
      nric: [],
      name: [],
      shop: ['', Validators.required],
      isEntryOrExit: [, Validators.required],
      dateTime: []
    });
    this.shopService.getShops().subscribe(
      shops => {
        this.SHOPS = shops;
        this.shopsToDisplay = lodash.cloneDeep(shops);
      },
      error => console.log(error));
  }

  get nric(): FormControl { return this.visitForm.get('nric') as FormControl; }
  get shop():FormControl { return this.visitForm.get('shop') as FormControl; }
  get name(): FormControl { return this.visitForm.get('name') as FormControl; }
  get isEntryOrExit(): FormControl { return this.visitForm.get('isEntryOrExit') as FormControl; }
  get dateTime(): FormControl { return this.visitForm.get('dateTime') as FormControl; }

  onSubmit(): void {
    console.log(this.visitForm.value);
    let visitVM = this.createVisitViewModelFromForm();    

    this.visistService.createVisit(visitVM).subscribe(
      response => {
        console.log(response);
        this.responseMessages.push("visit " + response.body.visitId + " submited");
      },
      (error: HttpErrorResponse) => {
        console.log(error);
        this.responseMessages.push(error.error);
      });   
    
  }

  createVisitViewModelFromForm(): VisitViewModel {
    let visitVM: VisitViewModel = new VisitViewModel();
    visitVM.shop = this.shop.value;
    visitVM.shop.visits = null; 

    let visitor: IVisitor = new Visitor();
    visitor.name = this.name.value;
    visitor.nric = this.nric.value;    
    visitVM.visitor = visitor;

    visitVM.entryOrExit = this.isEntryOrExit.value;   
    return visitVM;
  }

}
