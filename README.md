# Bati-Cuisine

## Titre
**Application d'estimation des coûts de construction des cuisines**

## Description rapide
Bati-Cuisine est une application Java destinée aux professionnels de la construction et de la rénovation de cuisines. Elle calcule le coût total des travaux en tenant compte des matériaux utilisés et du coût de la main-d'œuvre, facturée à l'heure. L'application inclut des fonctionnalités avancées telles que la gestion des clients, la génération de devis personnalisés, et une vue d'ensemble sur les aspects financiers et logistiques des projets de rénovation.

## Contexte
Le projet vise à offrir aux professionnels un outil puissant et pratique pour estimer avec précision les coûts et gérer efficacement les projets de rénovation de cuisines.

## Exigences fonctionnelles

### 1. Gestion des Projets
- **Ajouter un client** associé au projet.
- **Ajouter et gérer des composants** (matériaux, main-d'œuvre).
- **Associer un devis** au projet pour estimer les coûts avant les travaux.
- Un projet est caractérisé par:
    - `nomProjet (String)`: Nom du projet de construction ou de rénovation.
    - `margeBeneficiaire (double)`: Marge bénéficiaire appliquée au coût total.
    - `coutTotal (double)`: Coût total calculé pour le projet.
    - `etatProjet (Enum)`: Statut du projet (En cours, Terminé, Annulé).

### 2. Gestion des Composants

#### Matériaux
- Gestion des coûts des matériaux.
- Les matériaux sont caractérisés par:
    - `nom (String)`: Nom du composant.
    - `coutUnitaire (double)`: Coût unitaire du composant.
    - `quantite (double)`: Quantité de composants utilisée.
    - `typeComposant (String)`: Type de composant (Matériel ou Main-d'œuvre).
    - `tauxTVA (double)`: Taux de TVA applicable au composant.
    - `coutTransport (double)`: Coût du transport du matériau.
    - `coefficientQualite (double)`: Coefficient reflétant la qualité du matériau.

#### Main-d'œuvre
- Calcul des coûts basés sur le taux horaire, les heures travaillées, et la productivité des ouvriers.
- La main-d'œuvre est caractérisée par:
    - `nom (String)`: Nom du composant.
    - `typeComposant (String)`: Type de composant (Matériel ou Main-d'œuvre).
    - `tauxTVA (double)`: Taux de TVA applicable au composant.
    - `tauxHoraire (double)`: Taux horaire de la main-d'œuvre.
    - `heuresTravail (double)`: Nombre d'heures travaillées.
    - `productiviteOuvrier (double)`: Facteur de productivité des ouvriers.

### 3. Gestion des Clients
- **Enregistrer** les informations de base d’un client.
- **Différencier** les clients professionnels et particuliers, influençant les remises ou taxes appliquées.
- **Calculer et appliquer** des remises spécifiques selon le type de client (ex. : remise pour les clients réguliers ou professionnels).
- Un client est caractérisé par:
    - `nom (String)`: Nom du client.
    - `adresse (String)`: Adresse du client.
    - `telephone (String)`: Numéro de téléphone du client.
    - `estProfessionnel (boolean)`: Indique si le client est un professionnel.

### 4. Création de Devis
- **Générer un devis** avant le début des travaux, incluant une estimation des coûts des matériaux, de la main-d'œuvre, des équipements, et des taxes.
- Inclure une **date d’émission** et une **date de validité** du devis.
- Indiquer si le devis a été **accepté** par le client.
- Un devis est caractérisé par:
    - `montantEstime (double)`: Montant estimé du projet sur la base des devis.
    - `dateEmission (Date)`: Date d'émission du devis.
    - `dateValidite (Date)`: Date de validité du devis.
    - `accepte (boolean)`: Indique si le devis a été accepté par le client.

### 5. Calcul des Coûts
- **Intégrer** les coûts des composants (matériaux, main-d'œuvre) dans le calcul du coût total du projet.
- **Appliquer une marge bénéficiaire** pour obtenir le coût final du projet.
- Tenir compte des **taxes (TVA)** et des **remises** applicables.
- **Gérer les ajustements** des coûts basés sur la qualité des matériaux ou la productivité de la main-d'œuvre.

### 6. Affichage des Détails et Résultats
- **Afficher** les détails complets du projet (client, composants, coût total).
- **Afficher** les informations d'un client, d'un devis.
- **Générer** un récapitulatif détaillé du coût total incluant la main-d'œuvre, les matériaux, les taxes, et la marge bénéficiaire.

---

**Note**: Ce document détaille les exigences fonctionnelles de l'application Bati-Cuisine, fournissant une vue d'ensemble des fonctionnalités attendues et des éléments clés du projet.
