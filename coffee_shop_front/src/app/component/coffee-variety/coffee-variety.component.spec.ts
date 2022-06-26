import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoffeeVarietyComponent } from './coffee-variety.component';

describe('CoffeeVarietyComponent', () => {
  let component: CoffeeVarietyComponent;
  let fixture: ComponentFixture<CoffeeVarietyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CoffeeVarietyComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CoffeeVarietyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
