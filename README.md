# E-Learning Platform

Une plateforme d'apprentissage en ligne moderne développée avec JavaFX, offrant une expérience d'apprentissage interactive et engageante.

## 📝 Description

Cette application e-learning est une solution complète pour la gestion de l'apprentissage en ligne, construite avec JavaFX et utilisant les technologies modernes. Elle permet aux utilisateurs de gérer des cours, des contenus pédagogiques, et des interactions entre apprenants et formateurs.

### Fonctionnalités principales

- 🎓 Gestion des cours et du contenu pédagogique
- 👥 Gestion des utilisateurs (apprenants et formateurs)
- 💳 Système de paiement intégré avec Stripe
- 📊 Génération de rapports et factures PDF
- 📧 Système de notification par email
- 🔐 Authentification sécurisée avec chiffrement BCrypt

## 📋 Table des matières

- [Prérequis](#prérequis)
- [Installation](#installation)
- [Configuration](#configuration)
- [Utilisation](#utilisation)
- [Structure du projet](#structure-du-projet)
- [Technologies utilisées](#technologies-utilisées)
- [Contribution](#contribution)
- [Licence](#licence)

## ⚙️ Prérequis

- Java JDK 21 ou supérieur
- Maven 3.8+
- MySQL 8.0+
- JavaFX SDK 21.0.2
- Un compte Stripe (pour les paiements)
- Un serveur SMTP (pour les emails)

## 🚀 Installation

1. Clonez le repository :
```bash
git clone https://github.com/votre-username/e-learning.git
cd e-learning
```

2. Configurez la base de données MySQL :
```bash
mysql -u root -p
CREATE DATABASE elearning;
```

3. Installez les dépendances avec Maven :
```bash
mvn clean install
```

## ⚙️ Configuration

1. Créez un fichier `.env` à la racine du projet avec les configurations suivantes :
```properties
DB_URL=jdbc:mysql://localhost:3306/elearning
DB_USERNAME=votre_username
DB_PASSWORD=votre_password
STRIPE_API_KEY=votre_cle_stripe
SMTP_HOST=votre_smtp_host
SMTP_PORT=587
SMTP_USERNAME=votre_email
SMTP_PASSWORD=votre_password
```

## 💻 Utilisation

Pour lancer l'application :

```bash
mvn javafx:run
```

Ou exécutez directement depuis votre IDE en lançant la classe `tn.elearning.tests.MainFX`.

## 📁 Structure du projet

```
src/main/java/tn/elearning/
├── components/     # Composants réutilisables JavaFX
├── controller/     # Contrôleurs FXML
├── entities/       # Entités JPA
├── services/       # Services métier
├── tools/         # Utilitaires
└── utils/         # Classes utilitaires
```

## 🛠️ Technologies utilisées

- JavaFX 21.0.2 - Interface utilisateur
- Hibernate 6.2.7 - Persistance des données
- MySQL Connector 8.0.33 - Base de données
- Stripe Java 24.19.0 - Paiements
- iText PDF 5.5.13 - Génération de PDF
- Jakarta Mail 2.0.1 - Envoi d'emails
- Project Lombok - Réduction du boilerplate
- BCrypt - Sécurité des mots de passe
- SLF4J/Logback - Logging

## 🤝 Contribution

Les contributions sont les bienvenues ! Pour contribuer :

1. Forkez le projet
2. Créez une branche pour votre fonctionnalité (`git checkout -b feature/AmazingFeature`)
3. Committez vos changements (`git commit -m 'Add some AmazingFeature'`)
4. Poussez vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrez une Pull Request

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.
