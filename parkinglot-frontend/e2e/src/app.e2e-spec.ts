import { AppPage } from './app.po';
import { browser, by, element } from 'protractor';

describe('workspace-project App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('check in and check out process', () => {
    page.navigateTo();
    page.getLicensePlateInputText().clear();
    page.getLicensePlateInputText().sendKeys('ssh42a');
    page.getCylinderCapacityInputText().sendKeys('420');
    page.getCheckInButton().click();
    page.getSaveModalButton().click();
    expect(page.getToast().isPresent()).toBeTruthy();
    browser.getCurrentUrl().then(function(url) {
        expect(url).toEqual('http://localhost:4200/invoices'); 
    });
    page.getCheckOutButton().click();
    page.getConfirmModalCheckOut().click();
    expect(page.getToast().isPresent()).toBeTruthy();
    browser.getCurrentUrl().then(function(url) {
      expect(url).toEqual('http://localhost:4200/history'); 
  });
  });
});
