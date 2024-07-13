import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AggiungieventoComponent } from './aggiungievento.component';

describe('AggiungieventoComponent', () => {
  let component: AggiungieventoComponent;
  let fixture: ComponentFixture<AggiungieventoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AggiungieventoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AggiungieventoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
