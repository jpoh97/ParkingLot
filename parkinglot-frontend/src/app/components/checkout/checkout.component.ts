import { Component, OnInit } from '@angular/core';
import { VehicleService } from './../../services/vehicle.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  licensePlate: string = "";
  display = 'none';
  
  constructor(private service: VehicleService, private toastr: ToastrService, private router: Router) { }

  ngOnInit() {
  }

  isValidLicensePlate(): boolean {
    return (this.licensePlate.trim().length > 0)
  }

  validateData() {
    if(!this.isValidLicensePlate()){
      this.toastr.error('License plate cannot be null or empty', 'Error!');
      return;
    }
    this.openModal();
  }

  checkOutClick() {
    this.service.checkOut(this.licensePlate).subscribe(
      checkOutResponse => {
        this.router.navigate(['history']);
        this.toastr.success('Success checkout for vehicle ' + this.licensePlate);
      },
      (error) => {
        if (error.status !== 200) {          
          this.toastr.error(error.error['message'], 'Error!');
          this.closeModal();
        }
      });
  }

  onKey(event: any) { // without type info
    this.licensePlate = event.target.value;
  }

  openModal() {
    this.display = 'block';
  }

  closeModal() {
    this.display = 'none';
  }
}
