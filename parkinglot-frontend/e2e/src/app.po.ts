import { browser, by, element } from 'protractor';

export class AppPage {
  navigateTo() {
    return browser.get('/checkin');
  }

  getLicensePlateInputText() {
    return element(by.id('licensePlate'));
  }

  getCylinderCapacityInputText() {
    return element(by.id('cylinderCapacity'));
  }

  getCheckInButton() {
    return element(by.css('button.btn.btn-primary'));
  }

  getSaveModalButton() {
    return element(by.css('div.modal-footer > button.btn.btn-primary'));
  }

  getToast() {
    return element(by.css('div.toast-message.ng-star-inserted'));
  }

  getCheckOutButton() {
    return element(by.css('button.btn.btn-danger'));
  }

  getConfirmModalCheckOut() {
    return element(by.css('app-invoices.ng-star-inserted > div.modal > div.modal-dialog > div.modal-content > div.modal-footer > button.btn.btn-primary'));
  }
}
