$(document).ready(function() {

	//Darstellung
	
	$("#upload_table").hide();						//Verstecken der Tabellen zwecks benutzerfreundlicherer Darstellung
	$("#anno_table").hide();
	$("#bio_table").hide();
			 
	$("#annotation").accordion({					//jQueryUI Accordion Annotation
		//event:false,								//deaktivieren des Wechselns von Accordionen über Maus
		header: "> div > h3",						//Umwandeln von div h3-Strukturen in Accordions
		heightStyle: "content"						//Strecken des Accordions passend zu Inhalt
	});
	$("#bioModell, #upload").accordion({			//jQueryUI Accordione BioModell und Upload
		//event:false,								//deaktivieren des Wechselns von Accordionen per Maus
		heightStyle: "content"						//Strecken des Accordion passend zum Inhalt
	});
	
	$("#tabs").tabs();		//Rendern der Tabs durch jQueryUI
	
	$("#addBio, #addAnno, #upload_button, #anno_det, #bio_det, #upl_det").button();		//Rendern der Buttons durch jQueryUI
	
	//Tabellendefintionen
				
	var t_anno = $("#anno_table").DataTable({		//Tabelle für Annotation(-en)
		"columnDefs": [ {							//Spaltendefinitionen
			"targets": [ 0,3 ],						//betreffende Spalte(-n)
			"visible": false						//Sichtbarkeit aus
		}],
		"fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {	//Funktion zur Manipulation von Werten in einer Zeile
			switch(aData[5]){								//Ziel Spalte 5 Exists
				case true:									//Wenn die Annotation existiert, dann ...
					$(nRow).css('color', 'green')			//Ändern der Schriftfarbe der Zeile auf gruen
					$("td:eq(3)", nRow).html("");			//Setzen des Wertes der sichtbaren Spalte 3
					break;
				case false:									//Wenn die Annotation nicht existiert, dann ...
					// Einfügen einer Bilddatei in Spalte
					imgLink="images/att.jpg";								//relativer Link zum Bild
					var imgTag = '<img src="' + imgLink + '"/>';	//HTML Code zum Einbinden eines Bildes
					$("td:eq(3)", nRow).html(imgTag);				//Setzen des Bildes als Inalt in der sichtbaren Spalte 3
					$(nRow).css('color', 'red')		;				//Ändern der Schriftfarbe der Zeile in rot
					break;
			}
			switch(aData[6]){										//Ziel Spalte 6 Obsolete
				case true:											//Wenn Annotation obsolet, dann ...
					// Einfügen einer Bilddatei in Spalte
					imgLink="images/att.jpg";								//siehe Exists false
					var imgTag = '<img src="' + imgLink + '"/>';
					$('td:eq(4)', nRow).html(imgTag);
					$(nRow).css('color', 'red');
					break;
				case false:											//Wenn Annotation nicht obsolet, dann ...
					$(nRow).css("color", "green");					//siehe Exists true
					$("td:eq(4)", nRow).html("");
					break;
			}
		}
	});
	
	var t_bio = $("#bio_table").DataTable({			//siehe var t_anno
		"columnDefs": [ {
			"targets": [ 5 ],
			"visible": false
		}],
		"fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
			switch(aData[7]){
				case true:
					$(nRow).css('color', 'green');
					$("td:eq(6)", nRow).html("true");
					break;
				case false:
					imgLink="images/att.jpg";
					var imgTag = '<img src="' + imgLink + '">false</>';
					$("td:eq(6)", nRow).html(imgTag);
					$(nRow).css('color', 'red');
					break;
				case null:
					$("td:eq(6)", nRow).html("unknown");
					break;
			}
			switch(aData[8]){
				case true:
					imgLink="images/att.jpg";
					var imgTag = '<img src="' + imgLink + '">true</>';
					$('td:eq(7)', nRow).html(imgTag);
					$(nRow).css('color', 'red');
					break;
				case false:
					$(nRow).css("color", "green");
					$("td:eq(7)", nRow).html("false");
					break;
				case null:
					$("td:eq(7)", nRow).html("unknown");
					break;
			}
		}
	});
	
	var t_upl = $("#upload_table").DataTable({		//siehe var t_anno
		"columnDefs": [ {
			"targets": [ 5 ],
			"visible": false
		}],
		"fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
			switch(aData[7]){
				case true:
					$(nRow).css('color', 'green');
					$("td:eq(6)", nRow).html("");
					break;
				case false:
					imgLink="images/att.jpg";
					var imgTag = '<img src="' + imgLink + '"/>';
					$("td:eq(6)", nRow).html(imgTag);
					$(nRow).css('color', 'red');
					break;
				case null:
					$("td:eq(6)", nRow).html("unknown");
					break;
			}
			switch(aData[8]){
				case true:
					imgLink="images/att.jpg";
					var imgTag = '<img src="' + imgLink + '"/>';
					$('td:eq(7)', nRow).html(imgTag);
					$(nRow).css('color', 'red')
					break;
				case false:
					$(nRow).css("color", "green")
					$("td:eq(7)", nRow).html("unknown");
					break;
				case null:
					$("td:eq(7)", nRow).html("");
					break;
			}
		}
	});
	
	//Funktion zum Einfügen von Daten als Zeilen in die entsprechende Tabelle
	
	function insertDataInTable(table,typeName,modelelement) {			//table=Tabellenname, typeName=Name des Elements aus Modell, modelelement=Element des Modells
		if(modelelement) {									//Test ob Element des Modells vorhanden
			$.each(modelelement, function(k,v) {			//Iteration durch modelelement
				if(this.annotations) {						//Test auf vorhandene Annotationen
					$.each(this.annotations, function(m,annotation) {		//Iteration durch Annotationen
						if(annotation){										//Test auf Inhalt von Annotation
							var resource="", consider="", url="";			//Ressource(-n), Alternative(-n) und Link der Annotation
							url="<p><a class='resource' href='"+this.url+"' target='_blank'>"+this.url+"</a></p>";		//Setzen der AnnotationsURL
							if(annotation.resource) {									//Test auf vorhandene Ressourcen
								$.each(annotation.resource, function(key,value) {		//Iteration durch Ressourcen und Umwandeln in kleinere klickbare Links, öffnen in neuem Tab
									resource+="<a class='resource' href='"+value.url+"' target='_blank'><font size='-1'>"+value.name+"</font></a>";
									if(key<(annotation.resource.length-1)) {
										resource+="<br>";						//Zeilenumbruch für bessere Lesbarkeit
									}
								});
							} else {
								resource="No resources available.";				//Fallback falls keine Ressourcen verfügbar
							}
							if(annotation.consider) {									//Test auf vorhandene Alternativen
								$.each(annotation.consider, function(key,value) {		//Iteration durch Alternativen und Umwandeln in kleinere klickbare Links, öffnen in neuem Tab
									consider+="<a class='consider' href='"+value.url+"' target='_blank'><font size='-1'>"+value.name+"</font></a>";
									if(key<(annotation.consider.length-1)) {
										consider+="<br>";						//Zeilenumbruch für bessere Lesbarkeit
									}
								});
							} else {
								consider="No alternatives available.";			//Fallback falls keine Alternativen verfügbar
							}
							table.row.add( [						//Hinzufuegen der Zeile zur zugehörigen Tabelle table
								typeName,							//Name des Elements aus Modell
								v.id,								//ID der Annotation
								v.name,								//Name der Annotation
								url,								//URL der Annotation
								annotation.label,					//Label
								annotation.definition,				//Definition
								resource,							//QuellURLs
								annotation.exists,					//Existienz der Annotation
								annotation.obsolete,				//Obsoleszens der Annotation
								consider							////Alternativen falls Obsolet
							] ).draw();								////Einzeichnen in Tabelle
						}
					});
				}
			});
		}
	};
	
	//Tabellenfunktionen
	
	var index=1;				//Zähler für Index der Annotationen zum Ordnen
	
	$("#addAnno").click( function() {					//Funktion hinter "Check"-Button bie Annotation
		if(t_anno.column(3).visible()==true){			//Test auf sichtbarkeit der Spalte Definition
			t_anno.column(3).visible(false);			//Wenn sichtbar, dann erneut verbergen
		}
		var restUri="/AIDV/validate?annotation=";		//REST-URI
		var url=restUri+$("#search_anno").val();		//URL für Server mit Eingabe
		var resource="", consider="", orig_url="";		//URLs für Resourcen, Alternativen und Direkt
		$("#search_anno").val("");						//Setzen des Eingabefeldes in Annotation auf leer
		$.getJSON(url , function(anno) {				//Funktion zum Empfang der JSON
			orig_url+="<p><a class='resource' href='"+anno.url+"' target='_blank'>"+anno.url+"</a></p>";	//Umwandeln der DirektURL in eine klickbare Version
			if(anno.resource) {
				$.each(anno.resource, function(k,v) {		//Umwandeln der RessourcenURL in klickbare Version(-en), öffnen in neuem Tab
					resource+="<a class='resource' href='"+v.url+"' target='_blank'><font size='-1'>"+v.name+"</font></a>";
					if(k<(anno.resource.length-1)) {
						resource+="<br>";					//Zeilenumbruch für bessere Lesbarkeit
					}
				})
			} else {
				resource="No resources available.";
			}
			if(anno.consider){							//Umwandeln der AlternativenURL in kleinere klickbare Version(-en), öffnen in neuem Tab
				$.each(anno.consider, function(k,v) {
					consider+="<a class='resource' href='"+v.url+"' target='_blank'><font size='-1'>"+v.name+"</font></a>";
					if(k<(anno.consider.length-1)) {
						consider+="<br>";					//Zeilenumbruch für bessere Lesbarkeit
					}
				})
			} else {
				consider="No alternatives available.";		//Fallback falls keine Alternativen verfügbar.
			}
			t_anno.row.add( [							//Hinzufuegen der Zeile zur Annotationstabelle t_anno
				index,									//Index Der Annotation
				orig_url,								//URL der Annotation
				anno.label,								//Label
				anno.definition,						//Definition
				resource,								//QuellURLs
				anno.exists,							//Existienz der Annotation
				anno.obsolete,							//Obsoleszens der Annotation
				consider,								//Alternativen falls Obsolet
			] ).draw();									//Einzeichnen in Tabelle
			t_anno.order([0, 'desc']).draw();			//Ordnen von t_anno nach dem index, absteigend
			 $("#anno_table").show();					//Wiederanzeigen von t_anno
			index++;									//Erhöhen des Index um 1
		});
	});

	$("#addBio").click (function () {					//Funktion die im Tab BioModell ausgelöst wird
		if(t_bio.column(5).visible()==true){			//Wenn Spalte Defintion(5) sichtbar,
			t_bio.column(5).visible(false);				//dann wieder verstecken
		}
		t_bio.clear();									//Löschen des Inhalts der BioModelltabelle t_bio
		var restUri="/AIDV/validate?biomodel=";			//REST-URI
		var url=restUri+$("#search_bio").val();			//URL für Server mit Eingabe
		console.log(url);
		$("div.biomodell").html("Please wait while your request is processed.").css("color","green");	//Einfügen einer Wartenachricht solange die Tabeller erstellt wird, in grüner Schrift
		$.getJSON(url , function(model) {				//Funktion zum Empfang der JSON
			insertDataInTable(t_bio,"functionDefinition",model.functionDefinition);			//Einfügen der verschiedenen Elemente des Modells in die Tabelle t_bio
			insertDataInTable(t_bio,"unitDefinition",model.unitDefinition);
			insertDataInTable(t_bio,"compatementType",model.compartementType);
			insertDataInTable(t_bio,"speciesType",model.speciesType);
			insertDataInTable(t_bio,"compartement",model.compartement);
			insertDataInTable(t_bio,"species",model.species);
			insertDataInTable(t_bio,"parameter",model.parameter);
			insertDataInTable(t_bio,"initialAssignment",model.initialAssignment);
			insertDataInTable(t_bio,"assignmentRule",model.assignmentRule);
			insertDataInTable(t_bio,"constraint",model.constraint);						
			insertDataInTable(t_bio,"reaction",model.reaction);						
			insertDataInTable(t_bio,"event",model.event);
			t_bio.order([8, 'desc']).draw();						//Ordnen nach Spalte Obsolete (8), absteigend
			$("#bio_table").show();									//Wiederanzeigen der Tabelle t_bio
			$("div.biomodell").html("Your checking for: "+$("#search_bio").val()+" returned the following results.").css("color","black");
			//if(model.name!="null")$("#model_id_bio").html(model.id+": "+model.name);//Ersetzen der Wartenachricht durch Rückmeldung und Modell-Name und ID
			//else $("#model_id_bio").html(model.name);
		});
	});
	
	$("#upload_button").click (function () {			//Funktion die im Tab Modell-Upload ausgelöst wird
		if(t_upl.column(5).visible()==true){			//Wenn Spalte Defintion(5) sichtbar,
			t_upl.column(5).visible(false);				//dann wieder verstecken
		}
		t_upl.clear();									//Löschen des Inhalts der BioModelltabelle t_upl
		var options = {
				complete: function(response)			//Wenn Upload erfolgreich
				{
					$("div.uplmodell").html("Please wait while your request is processed.").css("color","green");		//Einfügen einer Wartenachricht solange die Tabeller erstellt wird, in grüner Schrift
	    	    	var model=$.parseJSON(response.responseText);
					insertDataInTable(t_upl,"functionDefinition",model.functionDefinition);		//Einfügen der verschiedenen Elemente des Modells in die Tabelle t_upl
					insertDataInTable(t_upl,"unitDefinition",model.unitDefinition);
					insertDataInTable(t_upl,"compatementType",model.compartementType);
					insertDataInTable(t_upl,"speciesType",model.speciesType);
					insertDataInTable(t_upl,"compartement",model.compartement);
					insertDataInTable(t_upl,"species",model.species);
					insertDataInTable(t_upl,"parameter",model.parameter);
					insertDataInTable(t_upl,"initialAssignment",model.initialAssignment);
					insertDataInTable(t_upl,"assignmentRule",model.assignmentRule);
					insertDataInTable(t_upl,"constraint",model.constraint);						
					insertDataInTable(t_upl,"reaction",model.reaction);						
					insertDataInTable(t_upl,"event",model.event);
					t_upl.order([8, 'desc']).draw();				//Ordnen nach Spalte Obsolete (8), absteigend
					$("#upload_table").show();						//Wiederanzeigen der Tabelle t_upl
					$("div.uplmodell").html("The validation-request for your biomodel returned the following results").css("color","black");		//Ersetzen der Wartenachricht durch Rückmeldung
					//Ersetzen der Wartenachricht durch Rückmeldung und Modell-Name und ID
					//	if(model.name!="null")$("#model_id_upl").html(model.id+": "+model.name);//Ersetzen der Wartenachricht durch Rückmeldung und Modell-Name und ID
					//	else $("#model_id_bio").html(model.name);
				},
	    	    error: function() {						//Wenn Upload NICHT erfolgreich
	    	        $("#message").html("<font color='red'> ERROR: unable to upload files</font>");			//Fehlermeldung  	 
	    	    }
	    	};
	    	$("#myForm").ajaxForm(options);
			$('#upload').accordion('option', 'active', ($('#upload').accordion('option','active') + 1 )); 		//Setzen des Result-Tabs als aktives Accordion im Upload-Tab
	});
	
	//Tabellen- und Akkordionfunktionen
				
	//Funktion zum Öffnen des Annotation-Result-Accordions
	$("#annotation button").click(function(e) {
		e.preventDefault();
		if($(this).is(".det")){							//Wird durch klick auf "Details" ausgelöst
			if(t_anno.column(3).visible()==true){		//Wenn Definition sichtbar,
				t_anno.column(3).visible(false);		//verstecken,
			} else {
				t_anno.column(3).visible(true);			//sonst sichtbar lassen
			}
		} else {											//Wird durch Klick auf "Check" ausgelöst
			var delta = ($(this).is('.next') ? 1 : -1);		//Bestimmt die Richtung der Aktivierung des naechsten Accordions
			$('#annotation').accordion('option', 'active', (
				$('#annotation').accordion('option','active') + delta )
			);												//Aktivieren des Accordions in Richtung delta
		}
	});
		
	//Funktion zum Öffnen des BioModell-Result-Accordions, siehe Anno-Result-Accordion
	$("#bioModell button").click(function(e) {
		e.preventDefault();
		if($(this).is(".det")){
			if(t_bio.column(5).visible()==true){
				t_bio.column(5).visible(false);
			} else {
				t_bio.column(5).visible(true);
			}
		} else {
			var delta = ($(this).is('.next') ? 1 : -1);
			$('#bioModell').accordion('option', 'active', (
				$('#bioModell').accordion('option','active') + delta )
			);
		}
	});
				
	//Funktion zum Überprüfen der Sichtbarkeit der Spalte Definition (5)
	$("#upload button").click(function(e) {
		e.preventDefault();
		if($(this).is(".det")){
			if(t_upl.column(5).visible()==true){
				t_upl.column(5).visible(false);
			} else {
				t_upl.column(5).visible(true);
			}
		}
	});

});