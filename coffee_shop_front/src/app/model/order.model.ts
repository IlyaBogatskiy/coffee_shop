import {OrderItem} from "./order-item.model";

export interface Order {

  id: number;

  dateTime: Date;

  customerName: string;

  deliveryAddress: string;

  orderPrice: number;

  orderItems: OrderItem[];
}
