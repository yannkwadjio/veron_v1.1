const baseURL="/veron"
const startDate=document.querySelector('#startDate');
const endDate=document.querySelector('#endDate');
const btnSearch=document.querySelector('#btn_search');
const tbody=document.querySelector('#table_mvt_stock tbody');
let tfoot=document.createElement('tfoot');

document.addEventListener('click', () => {
 const viewStore = document.querySelector('#viewStore');
  const btnAll = document.querySelectorAll("button");
  btnAll.forEach(btn => {
    btn.addEventListener('click', () => {
    if(btn!=btnSearch){
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
              const tbody=document.querySelector('#table_store tbody');
              tbody.innerHTML='';
              data.forEach(store=>{
              if(store.store==nameStore){
              let tr=document.createElement('tr');
                    tr.innerHTML=`<td>${store.refProduct}</td>
                                   <td>${store.category}</td>
                                   <td>${store.name}</td>
                                   <td>${store.finalStock}</td>
                                   <td>${store.lot}</td>
                                   <td>${store.dateExpiration}</td>

                               `
                                tbody.append(tr);
              }

              })
              }
              }
              getAllStoreByStock();

    }




    });
  });
  viewStore.click();
});


const viewMvtStock=document.querySelector('#viewMvtStock');



btnSearch.addEventListener('click',()=>{
async function getAllMvtStores(){
const url=`${baseURL}/api/v1/mvt-stock/get-all-mvt-by-date?startDate=${startDate.value}&endDate=${endDate.value}`;
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

data.forEach(mvtStock=>{
let tr=document.createElement('tr');

let td=document.createElement('td');
td.textContent=mvtStock.dateCreation;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.idOperation;;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.store01;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.store02;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.product;;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.initialStock;;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.incomingQuantity;;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.outgoingQuantity;;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtStock.finalStock;;
tr.append(td);


tbody.append(tr);

})


tr=document.createElement('tr');
tr.innerHTML = `<td colspan='8'></td>
                <td colspan='1'>
                 <form action="/export-stock-excel-date" method="post">
                   <input type="hidden" name="startDate" value="${startDate.value}">
                   <input type="hidden" name="endDate" value="${endDate.value}">
                   <button type="submit" class="btn btn-success px-3">Excel</button>
                 </form>
                </td>`;
tfoot.append(tr);
document.querySelector('table').append(tfoot);


                  $(document).ready(function () {
                    $('#table_mvt_stock').DataTable({
                     "destroy": true,
                      "paging": true,       // Activer la pagination
                      "searching": true,    // Activer la recherche (filtrage)
                      "ordering": true,     // Activer le tri sur les colonnes
                      "info": true,         // Afficher les informations de pagination
              "url": "//cdn.datatables.net/plug-ins/1.13.5/i18n/French.json"
                    });
                  });

}
}
}
getAllMvtStores();
})


const containerMvtStock=document.querySelector("#container_reporting_mvt_stock");
const containerStock=document.querySelector("#container_reporting_stock");

viewMvtStock.addEventListener("click",()=>{
containerStock.style.display='none';
containerMvtStock.style.display='inline-block';
})












