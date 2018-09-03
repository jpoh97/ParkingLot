import { Component, OnInit } from '@angular/core';
import { InvoiceService } from '../../services/invoice.service';
import { Invoice } from '../../models/invoice.model';
import { VehicleService } from './../../services/vehicle.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-invoices',
  templateUrl: './invoices.component.html',
  styleUrls: ['./invoices.component.css']
})
export class InvoicesComponent implements OnInit {

  invoices: [Invoice];
  resp: any;
  display = 'none';
  licensePlate = "";

  constructor(private service: InvoiceService, private vehicleService: VehicleService, private toastr: ToastrService, private router: Router) { }

  ngOnInit() {
    this.service.getOpenInvoices().subscribe(
      resp  => {
        this.resp = resp;
        this.invoices = this.resp['content'];
      }
    );
  }

  isValidLicensePlate(): boolean {
    return (this.licensePlate.trim().length > 0)
  }

  checkOutClick() {
    if(!this.isValidLicensePlate()) {
      this.toastr.error('License plate cannot be null or empty', 'Error!');
      return;
    }
    this.vehicleService.checkOut(this.licensePlate).subscribe(
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

  openModal(vehicleLicensePlate) {
    this.licensePlate = vehicleLicensePlate;
    this.display = 'block';
  }

  closeModal() {
    this.display = 'none';
  }
}
