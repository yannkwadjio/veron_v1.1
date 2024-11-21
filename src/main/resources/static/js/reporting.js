const baseURL="/veron"
const startDate=document.querySelector('#startDate');
const endDate=document.querySelector('#endDate');
const btnSearch=document.querySelector('#btn_search');
const tbody=document.querySelector('#table_mvt_cash tbody');
let tfoot=document.createElement('tfoot');


btnSearch.addEventListener('click',()=>{
async function getAllMvtCashes(){
const url=`${baseURL}/api/v1/mvt-cash/get-mvt-cash-by-date?startDate=${startDate.value}&endDate=${endDate.value}`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
if(data!=null){
tbody.innerHTML='';
tfoot.innerHTML='';

data.forEach(mvtCash=>{
let tr=document.createElement('tr');

let td=document.createElement('td');
td.textContent=mvtCash.dateMvtCash;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.agency;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.reference;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.type;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.motif;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.amount;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.fee;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.sens;
tr.append(td);


tbody.append(tr);

})

tr=document.createElement('tr');
tr.innerHTML = `<td colspan='7'></td>
                <td colspan='1'>
                 <form th:action="@{/export-cashs-excel-date}" method="post">
                   <input type="hidden" name="startDate" value="${startDate.value}">
                   <input type="hidden" name="endDate" value="${endDate.value}">
                   <button type="submit" class="btn btn-success px-3 btn-sm">Excel</button>
                 </form>
                </td>`;
tfoot.append(tr);
document.querySelector('#table_mvt_cash').append(tfoot);

new DataTable('#table_mvt_cash');
     $(document).ready(function () {
      $('#table_mvt_cash').DataTable({
       "destroy": true,
        "paging": true,
        "searching": true,
        "ordering": true,
        "info": true,
"url": "//cdn.datatables.net/plug-ins/1.13.5/i18n/French.json"
      });
    });

}
}
}
getAllMvtCashes();
})



const startDateStock=document.querySelector('#startDateStock');
const endDateStock=document.querySelector('#endDateStock');
const tbodyStock=document.querySelector('#table_mvt_stock tbody');
const btnSearchStock=document.querySelector('#btn_search_stock');

btnSearchStock.addEventListener('click',()=>{
async function getAllMvtStock(){
const url=`${baseURL}/api/v1/mvt-stock/get-all-mvt-by-date?startDate=${startDateStock.value}&endDate=${endDateStock.value}`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){

}else{
const data=await response.json();
if(data!=null){
tbodyStock.innerHTML='';
tfoot.innerHTML='';

data.forEach(mvtStock=>{
let tr=document.createElement('tr');

let td=document.createElement('td');
td.textContent=mvtStock.dateCreation;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.idOperation;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.store01;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.store02;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.category;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.product;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.initialStock;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.incomingQuantity;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.outgoingQuantity;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.finalStock;
tr.append(td);


tbodyStock.append(tr);

})


tr=document.createElement('tr');
tr.innerHTML = `<td colspan='9'></td>
                <td colspan='1'>
                 <form action="/export-stock-excel-date" method="post">
                   <input type="hidden" name="startDate" value="${startDateStock.value}">
                   <input type="hidden" name="endDate" value="${endDateStock.value}">
                   <button type="submit" class="btn btn-success px-3 btn-sm">Excel</button>
                 </form>
                </td>`;
tfoot.append(tr);
document.querySelector('#table_mvt_stock').append(tfoot);

     $(document).ready(function () {
      $('#table_mvt_stock').DataTable({
      "destroy": true,
        "paging": true,
        "searching": true,
        "ordering": true,
        "info": true,
"url": "//cdn.datatables.net/plug-ins/1.13.5/i18n/French.json"
      });
    });
}
}
}
getAllMvtStock();
})



const startDateCredit=document.querySelector('#startDateCredit');
const endDateCredit=document.querySelector('#endDateCredit');
const tbodyCredit=document.querySelector('#table_mvt_credit tbody');
const btnSearchCredit=document.querySelector('#btn_search_credit');

btnSearchCredit.addEventListener('click',()=>{
async function getAllMvtCredit(){
const url=`${baseURL}/api/v1/mvt-credits/get-all-by-date?startDateCredit=${startDateCredit.value}&endDateCredit=${endDateCredit.value}`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){

}else{
const data=await response.json();
if(data!=null){
tbodyCredit.innerHTML='';
tfoot.innerHTML='';

data.forEach(mvtCredit=>{
let tr=document.createElement('tr');

let td=document.createElement('td');
td.textContent=mvtCredit.dateCreation;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCredit.idMvtCredit;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCredit.source;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCredit.destination;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCredit.amount;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCredit.statutCredit;
tr.append(td);


tbodyCredit.append(tr);

})


tr=document.createElement('tr');
tr.innerHTML = `<td colspan='5'></td>
                <td colspan='1'>
                 <form action="/export-credit-excel-date" method="post">
                   <input type="hidden" name="startDate" value="${startDateCredit.value}">
                   <input type="hidden" name="endDate" value="${endDateCredit.value}">
                   <button type="submit" class="btn btn-success px-3 btn-sm">Excel</button>
                 </form>
                </td>`;
tfoot.append(tr);
document.querySelector('#table_mvt_credit').append(tfoot);

     $(document).ready(function () {
      $('#table_mvt_credit').DataTable({
      "destroy": true,
        "paging": true,
        "searching": true,
        "ordering": true,
        "info": true,
"url": "//cdn.datatables.net/plug-ins/1.13.5/i18n/French.json"
      });
    });

}
}
}
getAllMvtCredit();
})





const startDateOrder=document.querySelector('#startDateOrder');
const endDateOrder=document.querySelector('#endDateOrder');
const tbodyOrder=document.querySelector('#table_purchase_order tbody');
const btnSearchOrder=document.querySelector('#btn_search_order');
const statutOrder=document.querySelector('#statutOrder');


btnSearchOrder.addEventListener('click',()=>{
async function getAllMvtOrder(){
const url=`${baseURL}/api/v1/purchases-order/get-by-date?startDate=${startDateOrder.value}&endDate=${endDateOrder.value}`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){

}else{
const data=await response.json();
if(data!=null){
tbodyOrder.innerHTML='';
tfoot.innerHTML='';

data.forEach(mvtOrder=>{
if(mvtOrder.statutPurchase !="" && mvtOrder.statutPurchase==statutOrder.value){

let tr=document.createElement('tr');

let td=document.createElement('td');
td.textContent=mvtOrder.dateCreation;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.agency;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.refPurchaseOrder;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.products;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.priceHT;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.tva;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.priceTTC;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.remise;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.paymentMethod;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.statutPurchase;
tr.append(td);


tbodyOrder.append(tr);
}else if(statutOrder.value ==" Sélectionner un statut"){
let tr=document.createElement('tr');

let td=document.createElement('td');
td.textContent=mvtOrder.dateCreation;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.agency;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.refPurchaseOrder;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.products;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.priceHT;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.tva;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.priceTTC;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.remise;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.paymentMethod;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtOrder.statutPurchase;
tr.append(td);


tbodyOrder.append(tr);


}

})


tr=document.createElement('tr');
tr.innerHTML = `<td colspan='9'></td>
                <td colspan='1'>
                 <form action="/export-order-excel-date" method="post">
                   <input type="hidden" name="startDate" value="${startDateOrder.value}">
                   <input type="hidden" name="endDate" value="${endDateOrder.value}">
                   <button type="submit" class="btn btn-success px-3 btn-sm">Excel</button>
                 </form>
                </td>`;
tfoot.append(tr);
document.querySelector('#table_purchase_order').append(tfoot);

     $(document).ready(function () {
      $('#table_purchase_order').DataTable({
      "destroy": true,
        "paging": true,
        "searching": true,
        "ordering": true,
        "info": true,
"url": "//cdn.datatables.net/plug-ins/1.13.5/i18n/French.json"
      });
    });
}
}
}
getAllMvtOrder();
})




const startDateSale=document.querySelector('#startDateSale');
const endDateSale=document.querySelector('#endDateSale');
const tbodySale=document.querySelector('#table_mvt_sale tbody');
const btnSearchSale=document.querySelector('#btn_search_sale');


btnSearchSale.addEventListener('click',()=>{
async function getAllMvtSale(){
const url=`${baseURL}/api/v1/sales/get-mvt-sale-by-date?startDate=${startDateSale.value}&endDate=${endDateSale.value}`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){

}else{
const data=await response.json();
if(data!=null){
tbodySale.innerHTML='';
tfoot.innerHTML='';

data.forEach(mvtSale=>{

let tr=document.createElement('tr');

let td=document.createElement('td');
td.textContent=mvtSale.saleDate;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.agency;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.refSale;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.customer;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.service;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.products;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.paymentMethod;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.priceHT;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.remise;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.tva;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.priceTTC;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.lot;
tr.append(td);


tbodySale.append(tr);


})


tr=document.createElement('tr');
tr.innerHTML = `<td colspan='10'></td>
                <td colspan='1'>
                 <form action="/export-sale-excel-date" method="post">
                   <input type="hidden" name="startDate" value="${startDateSale.value}">
                   <input type="hidden" name="endDate" value="${endDateSale.value}">
                   <button type="submit" class="btn btn-success px-3 btn-sm">Excel</button>
                 </form>
                </td>`;
tfoot.append(tr);
document.querySelector('#table_mvt_sale').append(tfoot);

     $(document).ready(function () {
      $('#table_mvt_sale').DataTable({
      "destroy": true,
        "paging": true,
        "searching": true,
        "ordering": true,
        "info": true,
"url": "//cdn.datatables.net/plug-ins/1.13.5/i18n/French.json"
      });
    });
}
}
}
getAllMvtSale();
})




const startDateSaleD=document.querySelector('#startDateSaleD');
const endDateSaleD=document.querySelector('#endDateSaleD');
const tbodySaleD=document.querySelector('#table_mvt_saleD tbody');
const btnSearchSaleD=document.querySelector('#btn_search_saleD');


btnSearchSaleD.addEventListener('click',()=>{
async function getAllMvtSale(){
const url=`${baseURL}/api/v1/mvt-sales/get-by-date?startDate=${startDateSaleD.value}&endDate=${endDateSaleD.value}`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){

}else{
const data=await response.json();
if(data!=null){
tbodySaleD.innerHTML='';
tfoot.innerHTML='';

data.forEach(mvtSale=>{

let tr=document.createElement('tr');

let td=document.createElement('td');
td.textContent=mvtSale.saleDate;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.agency;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.refSale;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.customer;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.service;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.products;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.paymentMethod;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.priceHT;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.remise;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.tva;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.priceTTC;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtSale.lot;
tr.append(td);


tbodySaleD.append(tr);


})


tr=document.createElement('tr');
tr.innerHTML = `<td colspan='10'></td>
                <td colspan='1'>
                 <form action="/export-sale-excel-date" method="post">
                   <input type="hidden" name="startDate" value="${startDateSalesD.value}">
                   <input type="hidden" name="endDate" value="${endDateSaleD.value}">
                   <button type="submit" class="btn btn-success px-3 btn-sm">Excel</button>
                 </form>
                </td>`;
tfoot.append(tr);
document.querySelector('#table_mvt_saleD').append(tfoot);

     $(document).ready(function () {
      $('#table_mvt_saleD').DataTable({
      "destroy": true,
        "paging": true,
        "searching": true,
        "ordering": true,
        "info": true,
"url": "//cdn.datatables.net/plug-ins/1.13.5/i18n/French.json"
      });
    });
}
}
}
getAllMvtSale();
})









const startDateProfit=document.querySelector('#startDateProfit');
const endDateProfit=document.querySelector('#endDateProfit');
const tbodyProfit=document.querySelector('#table_mvt_profit tbody');
const btnSearchProfit=document.querySelector('#btn_search_profit');


btnSearchProfit.addEventListener('click',()=>{
async function getAllProfit(){
const url=`${baseURL}/api/v1/profit/get-all-by-date?startDate=${startDateProfit.value}&endDate=${endDateProfit.value}`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){

}else{
const data=await response.json();
if(data!=null){
tbodyProfit.innerHTML='';
tfoot.innerHTML='';

data.forEach(mvtProfit=>{

let tr=document.createElement('tr');

let td=document.createElement('td');
td.textContent=mvtProfit.idProfit;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtProfit.profitDate;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtProfit.enterprise;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtProfit.agency;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtProfit.montant;
tr.append(td);

tbodyProfit.append(tr);


})


tr=document.createElement('tr');
tr.innerHTML = `<td colspan='4'></td>
                <td colspan='1'>
                 <form action="/export-profit-excel-date" method="post">
                   <input type="hidden" name="startDate" value="${startDateProfit.value}">
                   <input type="hidden" name="endDate" value="${endDateProfit.value}">
                   <button type="submit" class="btn btn-success px-3 btn-sm">Excel</button>
                 </form>
                </td>`;
tfoot.append(tr);
document.querySelector('#table_mvt_profit').append(tfoot);

     $(document).ready(function () {
      $('#table_mvt_profit').DataTable({
      "destroy": true,
        "paging": true,
        "searching": true,
        "ordering": true,
        "info": true,
"url": "//cdn.datatables.net/plug-ins/1.13.5/i18n/French.json"
      });
    });
}
}
}
getAllProfit();
})



const startDateSpent=document.querySelector('#startDateSpent');
const endDateSpent=document.querySelector('#endDateSpent');
const btnSearchSpent=document.querySelector('#btn_search_spent');
const tbodySpent=document.querySelector('#table_mvt_spent tbody');


btnSearchSpent.addEventListener('click',()=>{
async function getAllMvtSpents(){
const url=`${baseURL}/api/v1/mvt-cash/get-mvt-cash-by-date?startDate=${startDateSpent.value}&endDate=${endDateSpent.value}`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
if(data!=null){
tbodySpent.innerHTML='';
tfoot.innerHTML='';

data.forEach(mvtCash=>{
if(mvtCash.type=='DEPENSES'){
let tr=document.createElement('tr');

let td=document.createElement('td');
td.textContent=mvtCash.dateMvtCash;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.agency;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.reference;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.type;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.motif;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.amount;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.fee;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.sens;
tr.append(td);


tbodySpent.append(tr);

}

})


tr=document.createElement('tr');
tr.innerHTML = `<td colspan='8'></td>
                <td colspan='1'>
                 <form action="/export-spent-excel-date" method="post">
                   <input type="hidden" name="startDate" value="${startDateSpent.value}">
                   <input type="hidden" name="endDate" value="${endDateSpent.value}">
                   <button type="submit" class="btn btn-success px-3 btn-sm">Excel</button>
                 </form>
                </td>`;
tfoot.append(tr);
document.querySelector('#table_mvt_spent').append(tfoot);

     $(document).ready(function () {
      $('#table_mvt_spent').DataTable({
      "destroy": true,
        "paging": true,
        "searching": true,
        "ordering": true,
        "info": true,
"url": "//cdn.datatables.net/plug-ins/1.13.5/i18n/French.json"
      });
    });
}
}
}
getAllMvtSpents();
})





document.addEventListener('click', () => {
 const viewStore = document.querySelector('#viewStore');
  const btnAll = document.querySelectorAll("#card-store button");
  btnAll.forEach(btn => {
    btn.addEventListener('click', () => {
      const btn_span = btn.querySelector('span');

  var nameStore=btn_span.innerText;
      const tableStore=document.querySelector('#table_store');
      async function getAllStoreByStock(){
      const url=`${baseURL}/api/v1/products-stock/get-all`;
      const response=await fetch(url, {
                                        method: 'GET',
                                        headers: {
                                            'Content-Type': 'application/json'
                                        }
                                    });
      if(!response.ok){
      }else{
      const data=await response.json();
      const tbodyStores=document.querySelector('#table_store tbody');
      tbodyStores.innerHTML='';
      data.forEach(store=>{
      if(store.store==nameStore){
      let tr=document.createElement('tr');
            tr.innerHTML=`<td>${store.refProduct}</td>
                           <td>${store.category}</td>
                           <td>${store.name}</td>
                           <td>${store.finalStock}</td>
                           <td>${store.lot}</td>
                           <td>${store.dateExpiration}</td>
                           <td>${store.purchasePrice}</td>
                       `
                        tbodyStores.append(tr);
      }

      })
      }
      }
      getAllStoreByStock();


    });
  });
  viewStore.click();
});



const startDateMissing=document.querySelector('#startDateMissing');
const endDateMissing=document.querySelector('#endDateMissing');
const btnSearchMissing=document.querySelector('#btn_search_missing');
const tbodyMissing=document.querySelector('#table_mvt_missing tbody');

btnSearchMissing.addEventListener('click',()=>{
async function getAllMissing(){
const url=`${baseURL}/api/v1/missing-cash/get-by-date?startDate=${startDateMissing.value}&endDate=${endDateMissing.value}`;
const response=await fetch(url, {
                                  method: 'GET',
                                  headers: {
                                      'Content-Type': 'application/json'
                                  }
                              });
if(!response.ok){
}else{
const data=await response.json();
if(data!=null){
tbodyMissing.innerHTML='';
tfoot.innerHTML='';

data.forEach(mvtMissing=>{
let tr=document.createElement('tr');

let td=document.createElement('td');
td.textContent=mvtMissing.dateCreation;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtMissing.agency;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtMissing.refMissingCash;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtMissing.responsible;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtMissing.amount;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtMissing.advance;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtMissing.rest;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtMissing.statut;
tr.append(td);


tbodyMissing.append(tr);

})


tr=document.createElement('tr');
tr.innerHTML = `<td colspan='6'></td>
                <td colspan='1'>
                 <form action="/export-missing-excel-date" method="post">
                   <input type="hidden" name="startDate" value="${startDateMissing.value}">
                   <input type="hidden" name="endDate" value="${endDateMissing.value}">
                   <button type="submit" class="btn btn-success px-3">Excel</button>
                 </form>
                </td>`;
tfoot.append(tr);
document.querySelector('#table_mvt_missing').append(tfoot);

     $(document).ready(function () {
      $('#table_mvt_missing').DataTable({
      "destroy": true,
        "paging": true,
        "searching": true,
        "ordering": true,
        "info": true,
"url": "//cdn.datatables.net/plug-ins/1.13.5/i18n/French.json"
      });
    });

}
}
}
getAllMissing();
})







