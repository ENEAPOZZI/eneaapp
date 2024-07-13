import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DettaglioeventoComponent } from './dettaglioevento.component';

describe('DettaglioeventoComponent', () => {
  let component: DettaglioeventoComponent;
  let fixture: ComponentFixture<DettaglioeventoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DettaglioeventoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DettaglioeventoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
