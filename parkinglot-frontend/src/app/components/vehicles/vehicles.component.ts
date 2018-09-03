import { Component, OnInit } from '@angular/core';
import { VehicleService } from './../../services/vehicle.service';
import { Vehicle } from './../../models/vehicle.model';

@Component({
  selector: 'vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.css']
})
export class VehiclesComponent implements OnInit {
  vehicles: [Vehicle];
  resp: any;
  constructor(private service: VehicleService) { }

  ngOnInit() {
    this.service.getAll().subscribe(
      resp  => {
        this.resp = resp;
        this.vehicles = this.resp['content'];
      }
    );
  }

}
