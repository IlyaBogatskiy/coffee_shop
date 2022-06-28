import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CoffeeVariety} from "../../model/coffee-variety.component";

@Component({
  selector: 'app-coffee-variety',
  templateUrl: './coffee-variety.component.html',
  styleUrls: ['./coffee-variety.component.css']
})
export class CoffeeVarietyComponent implements OnInit {

  coffeeVarieties: CoffeeVariety[] | undefined;

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.getOrders();
  }

  getOrders(){
    this.httpClient.get<any>('http://localhost:8082/coffee_shop/variety/all_available').subscribe(
      response => {
        console.log(response);
        this.coffeeVarieties = response;
      }
    );
  }

}
