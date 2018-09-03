import { Vehicle } from './../../models/vehicle.model';
import { Component, OnInit } from '@angular/core';
import { VehicleService } from './../../services/vehicle.service';
import { Router } from '@angular/router';

class VehcileClass implements Vehicle {
  licensePlate: string;  
  cylinderCapacity: number;
  vehicleTypeName: string;
  constructor(licensePlate: string,  cylinderCapacity: number, vehicleTypeName: string) { 
    this.licensePlate = licensePlate;  
    this.cylinderCapacity = cylinderCapacity;
    this.vehicleTypeName = vehicleTypeName;
  }
}

@Component({
  selector: 'checkin',
  templateUrl: './checkin.component.html',
  styleUrls: ['./checkin.component.css']
})
export class CheckinComponent implements OnInit {
  vehicleTypes = [
    {id:'1', name:'CAR'},
    {id:'2', name:'MOTORCYCLE'}
  ];
  licensePlate: string = "";
  cylinderCapacity: number = 0;
  currentVehicleType = "CAR";
  vehicle: Vehicle;

  constructor(private service: VehicleService, private router: Router) { }

  ngOnInit() {
  }

  checkInClick() {
    console.log(this.licensePlate);
    this.vehicle = new VehcileClass(this.licensePlate, this.cylinderCapacity, this.currentVehicleType);
    console.log(this.vehicle);
    this.service.checkIn(this.vehicle).subscribe();
    this.router.navigate(["/invoices"]);
  }

  onKeyLicensePlate(event: any) {
    this.licensePlate = event.target.value;
  }

  onKeyCylinderCapacity(event: any) { 
    this.cylinderCapacity = event.target.value;
  }

  changeType(event: any) {
    this.currentVehicleType = event.value;
  }
}
