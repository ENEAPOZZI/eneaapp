import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginapersonaleutenteComponent } from './paginapersonaleutente.component';

describe('PaginapersonaleutenteComponent', () => {
  let component: PaginapersonaleutenteComponent;
  let fixture: ComponentFixture<PaginapersonaleutenteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PaginapersonaleutenteComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PaginapersonaleutenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
