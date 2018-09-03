import { Component, OnInit } from '@angular/core';
import { InvoiceService } from '../../services/invoice.service';
import { Invoice } from '../../models/invoice.model'

@Component({
  selector: 'history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  invoices: [Invoice];
  resp: any;
  constructor(private service: InvoiceService) { }

  ngOnInit() {
    this.service.getAll().subscribe(
      resp  => {
        this.resp = resp;
        this.invoices = this.resp['content'];
      }
    );
  }

}
