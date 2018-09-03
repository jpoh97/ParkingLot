import { Component, OnInit } from '@angular/core';
import { InvoiceService } from '../../services/invoice.service';
import { Invoice } from '../../models/invoice.model'

@Component({
  selector: 'app-invoices',
  templateUrl: './invoices.component.html',
  styleUrls: ['./invoices.component.css']
})
export class InvoicesComponent implements OnInit {
  invoices: [Invoice];
  resp: any;
  constructor(private service: InvoiceService) { }

  ngOnInit() {
    this.service.getOpenInvoices().subscribe(
      resp  => {
        this.resp = resp;
        this.invoices = this.resp['content'];
      }
    );
  }

}
