import {OrderItem} from "./order-item.component";

export interface Order {

  id: number;

  orderDateTime: Date;

  customerName: string;

  deliveryAddress: string;

  orderPrice: number;

  orderItems: OrderItem[];
}
