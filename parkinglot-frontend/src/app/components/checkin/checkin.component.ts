import { Vehicle, VehcileClass } from './../../models/vehicle.model';
import { Component, OnInit } from '@angular/core';
import { VehicleService } from './../../services/vehicle.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

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
  display = 'none';
  response: any;
  disabled = false;

  constructor(private service: VehicleService, private toastr: ToastrService, private router: Router) { }

  ngOnInit() {
  }

  searchVehicle(license) {
    this.disabled = false;
    if(this.isValidLicensePlate()) {
      this.service.getOne(this.licensePlate).subscribe(
        vehicleResponse => {
          this.response = vehicleResponse;
          this.vehicle = this.response;
          this.cylinderCapacity = this.vehicle.cylinderCapacity;
          this.currentVehicleType = this.vehicle.vehicleTypeName;
          this.disabled = true;
        }
      )
    }
  }

  isValidLicensePlate(): boolean {
    return (this.licensePlate.trim().length > 0)
  }

  isValidCylinderCapacity(): boolean {
    return (null != this.cylinderCapacity && undefined != this.cylinderCapacity && this.cylinderCapacity.toString() != ""
      && this.cylinderCapacity >= 0)
  }
  
  validateData(licensePlateInput, cylinderCapacityInput) {
    this.licensePlate = licensePlateInput;
    this.cylinderCapacity = cylinderCapacityInput;
    if(!this.isValidLicensePlate()){
      this.toastr.error('License plate cannot be null or empty', 'Error!');
      return;
    }
    if(!this.isValidCylinderCapacity()){
      this.toastr.error('Cylinder capacity must be greater than zero', 'Error!');
      return;
    }
    this.licensePlate = this.licensePlate.toUpperCase();
    this.openModal();
  }

  checkInClick() {
    this.vehicle = new VehcileClass(this.licensePlate, this.cylinderCapacity, this.currentVehicleType);
    this.service.checkIn(this.vehicle).subscribe(
      checkInResponse => {
        this.router.navigate(['invoices']);
        this.toastr.success('Success CheckIn for vehicle ' + this.licensePlate);
      },
      (error) => {
        if (error.status !== 201) {          
          this.toastr.error(JSON.parse(error._body)['message'], 'Error!');
          this.closeModal();
        }
      });
  }

  onKeyLicensePlate(event: any) {
    this.licensePlate = event.target.value;
    this.disabled = false;
  }

  onKeyCylinderCapacity(event: any) { 
    this.cylinderCapacity = event.target.value;
  }

  changeType(event: any) {
    this.currentVehicleType = event.value;
  }

  openModal() {
    this.display = 'block';
  }

  closeModal() {
    this.display = 'none';
  }
}
