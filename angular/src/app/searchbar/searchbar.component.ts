import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-searchbar',
  templateUrl: './searchbar.component.html',
  styleUrls: ['./searchbar.component.css']
})
export class SearchbarComponent implements OnInit {

  constructor() { }

  @Input() parentForm: FormGroup;

  ngOnInit(): void {
    this.onChanges();
  }

  get searchText(): FormControl { return this.parentForm.get("searchText") as FormControl; }


  onChanges(): void {
    this.searchText.valueChanges.subscribe(value => console.log("from searchBar:", value));
  }

}
