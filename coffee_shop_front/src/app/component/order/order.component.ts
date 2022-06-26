import { Component, OnInit } from '@angular/core';
import {Order} from "../../model/order.component";
import {HttpClient} from "@angular/common/http";
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {NgForm} from "@angular/forms";
import {OrderItem} from "../../model/order-item.component";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  orders: Order[] | undefined;
  orderItems: OrderItem[] | undefined;
  closeResult: string | undefined;

  constructor(private httpClient: HttpClient,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this.getOrders();
  }

  getOrders(){
    this.httpClient.get<any>('http://localhost:8082/coffee_shop/order/all').subscribe(
      response => {
        console.log(response);
        this.orders = response;
      }
    );
  }

  open({content}: { content: any }) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  onSubmit(f: NgForm) {
    const url = 'http://localhost:8082/coffee_shop/order/create';
    this.httpClient.post(url, f.value)
      .subscribe((result) => {
        this.ngOnInit();
      });
    this.modalService.dismissAll();
  }

  onSubmitItem(f: NgForm) {
    const url = 'http://localhost:8082/coffee_shop/order/create_item';
    this.httpClient.post(url, f.value)
      .subscribe((result) => {
        this.ngOnInit();
      });
    this.modalService.dismissAll();
  }
}
