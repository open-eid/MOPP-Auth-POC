/*
 * Copyright 2017 Riigi Infosüsteemide Amet
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

if($("#callAjax").length > 0){

    $(document).ready(function(){
        var contextPath=window.location.pathname;
        var hash = $( "#hashbase64" ).text();
        var nationalIdentityNumber = $( "#nationalIdentityNumber" ).text();
        $.ajax({

            contentType: "application/json; charset=utf-8",
            url: 'authentication',
            data: ({hash : hash, nationalIdentityNumber : nationalIdentityNumber}),
            success: function(data) {
                console.log(data);
                $( "body" ).html( data );
                window.history.pushState("", "Title", "/validate");

            }
          });
  });
};
