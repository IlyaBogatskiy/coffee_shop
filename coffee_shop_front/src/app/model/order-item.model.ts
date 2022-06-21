import {CoffeeVariety} from "./coffee-variety.model";

export interface OrderItem {

  id: number;

  coffeeVariety: CoffeeVariety;

  cups: number;
}
