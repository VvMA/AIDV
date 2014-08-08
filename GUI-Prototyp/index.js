$(document).ready(function() {
			
				$("#annotation").accordion({
					//event:false,
					header: "> div > h3",
					heightStyle: "content"
				});
				$("#bioModell, #upload").accordion({
					//event:false,
					heightStyle: "content"
				});
				
				$("#tabs").tabs();
				
				var t_bio = $("#bio_table").DataTable({
					"fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
						switch(aData[7]){
							case false:
								// Einfügen einer Bilddatei in Spalte
								/**imgLink="jack.jpg";
								var imgTag = '<img src="' + imgLink + '"/>';
								$('td:eq(2)', nRow).html(imgTag);*/
								$(nRow).css('color', 'green')
								break;
							case true:
								$(nRow).css('color', 'red')
								break;
						}
					}
				});
				
				var t_upl = $("#upload_table").DataTable({
					"fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
						switch(aData[7]){
							case false:
								// Einfügen einer Bilddatei in Spalte
								/**imgLink="jack.jpg";
								var imgTag = '<img src="' + imgLink + '"/>';
								$('td:eq(2)', nRow).html(imgTag);*/
								$(nRow).css('color', 'green')
								break;
							case true:
								$(nRow).css('color', 'red')
								break;
						}
					}
				});
				
				$('#addAnno').click( function() {
					$("#search_anno").val("");
					var newDiv = "<div><h3><a href='#'>";
					var url="json/annotation.json";
					$.getJSON(url , function(data) {
						newDiv+=data.label;
						newDiv+="</a></h3><div><div><h3>Defintion:</h3>";
						newDiv+=data.definition;
						newDiv+="</div><div><h3>Resource:</h3>";
						if(data.resource!=null){
							$.each(data.resource, function(k,v) {
								newDiv+="<a class='resource' href='"+v.url+"'>"+v.name+"</a>";
								if(k<(data.resource.length-1)){
									newDiv+="<br></br>";
								}
							})
						} else {
							newDiv+="Keine Resourcen verf&uumlgbar.";
						}
						newDiv+="</div><div><h3>Obsolete:</h3>";
						newDiv+=data.obsolete;
						newDiv+="</div><div><h3>Exists:</h3>";
						newDiv+=data.exists;
						newDiv+="</div><div><h3>Consider:</h3>";
						if(data.consider!=null){
							$.each(data.consider, function(k,v) {
								newDiv+="<a class='resource' href='"+v.url+"'>"+v.name+"</a>";
								if(k<(data.consider.length-1)){
									newDiv+="<br></br>";
								}
							})
						} else {
							newDiv+="Keine Alternativen verf&uumlgbar.";
						}
						newDiv+="</div></div></div>";
						$('div.annotation').append(newDiv)
						$('div.annotation').accordion("refresh")
						var delta = ($(this).is('.next') ? 1 : -1);
						$('#annotation').accordion('option', 'active', (
							$('#annotation').accordion('option','active') + delta )
						);
					});
				});
 
				$("#addBio").click (function () {
					$("div.biomodell").append("Ihre Suche nach: "+$("#search_bio").val()+" lieferte folgende Ergebnis.");
					$("#search_bio").val("");
					var url="json/BIOMODEL.json";
					var bio="";
					$.getJSON(url , function(model) {
						$.each(model.species, function(k,v) {
							$.each(this.annotations, function(m,n) {
								bio+=" key: "+m+" value: "+n.url;
								t_bio.row.add( [
									v.id,
									v.name,
									n.url,
									n.label,
									n.definition,
									n.resource,
									n.exists,
									n.obsolete,
									n.consider
								] ).draw();
							})
						})
					});
				});
				
				$("#upload_button").click (function () {
					$("#search_upload").val("");
					/**var url="json/BIOMODEL.json";
					var bio="";
					$.getJSON(url , function(model) {
						$("div.biomodell").append("Ihre Validierungsanfrage für "+$("#search_upload").val()+" lieferte folgende Ergebnis.");
						$.each(model.species, function(k,v) {
							$.each(this.annotations, function(m,n) {
								bio+=" key: "+m+" value: "+n.url;
								t_upl.row.add( [
									v.id,
									v.name,
									n.url,
									n.label,
									n.definition,
									n.resource,
									n.exists,
									n.obsolete,
									n.consider
								] ).draw();
							})
						})
					});*/
				});
				
				$("#bioModell button").click(function(e) {
					e.preventDefault();
					var delta = ($(this).is('.next') ? 1 : -1);
					$('#bioModell').accordion('option', 'active', (
						$('#bioModell').accordion('option','active') + delta )
					);
					
					if(delta==1){
						var url="json/BIOMODEL.json";
						$.getJSON(url , function(data) {
							$("#model_id").html(data.id+": "+data.name);
						});
					}
				});
				
				$("#upload button").click(function(e) {
					e.preventDefault();
					var delta = ($(this).is('.next') ? 1 : -1);
					$('#upload').accordion('option', 'active', (
						$('#upload').accordion('option','active') + delta )
					);
				});
				
				
				
				$("#end_button, #addBio, #addAnno, #upload_button").button();
				
			});