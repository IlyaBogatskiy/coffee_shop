import {CoffeeVariety} from "./coffee-variety.component";

export interface OrderItem {

  id: number;

  coffeeVariety: CoffeeVariety;

  cups: number;
}
