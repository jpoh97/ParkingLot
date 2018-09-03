import { Component, OnInit } from '@angular/core';
import { VehicleService } from './../../services/vehicle.service';
import { Router } from '@angular/router';

@Component({
  selector: 'checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  licensePlate: string = "";
  
  constructor(private service: VehicleService, private router: Router) { }

  ngOnInit() {
  }

  checkOutClick() {
    this.service.checkOut(this.licensePlate).subscribe();
    this.router.navigate(["/history"]);
  }

  onKey(event: any) { // without type info
    this.licensePlate = event.target.value;
  }
}
