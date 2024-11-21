const baseURL="/veron"
const startDate=document.querySelector('#startDate');
const endDate=document.querySelector('#endDate');
const btnSearch=document.querySelector('#btn_search');
const tbody=document.querySelector('table tbody');
let tfoot=document.createElement('tfoot');
const nomUtil=document.querySelector('#nomUtil');

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
if(mvtCash.userCreated==nomUtil.innerText){
let tr=document.createElement('tr');

let td=document.createElement('td');
td.textContent=mvtCash.idMvtCash;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.dateMvtCash;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.agency;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.type;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.reference;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.motif;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.sens;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.amount;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.fee;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.balanceBefore;
tr.append(td);

td=document.createElement('td');
td.textContent=mvtCash.balanceAfter;
tr.append(td);

tbody.append(tr);
}
})


tr=document.createElement('tr');
tr.innerHTML = `<td colspan='10'></td>
                <td colspan='1'>
                 <form action="/export-cash-excel-date" method="post">
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
getAllMvtCashes();
})


