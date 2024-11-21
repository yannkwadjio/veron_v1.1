const baseURL="/veron"
const nomUtil=document.querySelector('#nomUtil');
const startDate=document.querySelector('#startDate');
const endDate=document.querySelector('#endDate');
const btnSearch=document.querySelector('#btn_search');
const tbody=document.querySelector('table tbody');
let tfoot=document.createElement('tfoot');

btnSearch.addEventListener('click',()=>{
async function getAllMvtSales(){
const url=`${baseURL}/api/v1/sales/get-mvt-sale-by-date?startDate=${startDate.value}&endDate=${endDate.value}`;
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

data.forEach(sale=>{
if(sale.userCreated==nomUtil.innerText){
let tr=document.createElement('tr');

let td=document.createElement('td');
td.textContent=sale.idSale;
tr.append(td);

td=document.createElement('td');
td.textContent=sale.saleDate;
tr.append(td);

td=document.createElement('td');
td.textContent=sale.agency;
tr.append(td);

td=document.createElement('td');
td.textContent=sale.service;
tr.append(td);

td=document.createElement('td');
td.textContent=sale.products;
tr.append(td);

td=document.createElement('td');
td.textContent=sale.paymentMethod;
tr.append(td);

td=document.createElement('td');
td.textContent=sale.refSale;
tr.append(td);

td=document.createElement('td');
td.textContent=sale.priceHT;
tr.append(td);

td=document.createElement('td');
td.textContent=sale.remise;
tr.append(td);

td=document.createElement('td');
td.textContent=sale.tva;
tr.append(td);

td=document.createElement('td');
td.textContent=sale.priceTTC;
tr.append(td);

td=document.createElement('td');
td.textContent=sale.lot;
tr.append(td);


tbody.append(tr);

}
})

tr=document.createElement('tr');
tr.innerHTML = `<td colspan='10'></td>
                <td colspan='1'>
                 <form action="/export-sale-excel-date" method="post">
                   <input type="hidden" name="startDate" value="${startDate.value}">
                   <input type="hidden" name="endDate" value="${endDate.value}">
                   <button type="submit" class="btn btn-success px-3 btn-sm">Excel</button>
                 </form>
                </td>`;
tfoot.append(tr);
document.querySelector('table').append(tfoot);

     $(document).ready(function () {
      $('table').DataTable({
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
getAllMvtSales();
})
