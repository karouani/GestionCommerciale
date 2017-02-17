<label for="nomClient">Nom <span class="requis">*</span></label>
<input type="text" id="nomClient" name="nomClient" size="30" maxlength="30" value="<c:out value="${client.nom}"/>"/>
<span class="erreur">${form.erreurs['nomClient']}</span>
<br />

<label for="prenomClient">Prénom <span class="requis">*</span></label>
<input type="text" id="prenomClient" name="prenomClient" size="30" maxlength="30"  value="<c:out value="${client.prenom}"/>"/>
<span class="erreur">${form.erreurs['prenomClient']}</span>
<br />
    
<label for="adresseClient">Adresse de livraison <span class="requis">*</span></label> 
<input type="text" id="adresseClient" name="adresseClient" size="30" maxlength="60"  value="<c:out value="${client.adresse}"/>"/>
<span class="erreur">${form.erreurs['adresseClient']}</span>
<br />

<label for="telephoneClient">Numéro de téléphone <span class="requis">*</span></label>
<input type="text" id="telephoneClient" name="telephoneClient" size="30" maxlength="30"  value="<c:out value="${client.telephone}"/>"/>
<span class="erreur">${form.erreurs['telephoneClient']}</span>
<br />

<label for="emailClient">Adresse email <span class="requis">*</span></label>
<input type="email" id="emailClient" name="emailClient" size="30" maxlength="60"  value="<c:out value="${client.email}"/>"/>
<span class="erreur">${form.erreurs['emailClient']}</span>
<br />

<label for="imageClient">Image</label>
<input type="file" id="imageClient" name="imageClient" />
<span class="erreur">${form.erreurs['imageClient']}</span>
<br />