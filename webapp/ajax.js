/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function getTable(name, tableId) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById(tableId).innerHTML = this.responseText;
    }
  };
  
  xhttp.open("GET", "DisplayAllItems?name="+document.getElementById(name).value, true);
  xhttp.send();
}
