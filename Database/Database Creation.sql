CREATE TABLE dbo.UserProfile
(
	ProfileId INT IDENTITY(1,1) PRIMARY KEY,
	Email VARCHAR(300) UNIQUE NOT NULL,
	PasswordHash VARCHAR(300) NOT NULL,
)

CREATE TABLE dbo.Allergy
(
	AllergyId INT IDENTITY(1,1) PRIMARY KEY,
	AllergyName VARCHAR(100) NOT NULL,
	AllergyDescription VARCHAR(300)
)

CREATE TABLE dbo.Mealplan
(
	MealplanId INT IDENTITY(1,1) PRIMARY KEY,
	MealplanName VARCHAR(100) NOT NULL,
	MealplanDescription VARCHAR(300)
)

CREATE TABLE dbo.UserAllergy
(
	UserAllergyId INT IDENTITY(1,1) PRIMARY KEY,
	ProfileId INT FOREIGN KEY REFERENCES dbo.UserProfile(ProfileId),
	AllergyId INT FOREIGN KEY REFERENCES dbo.Allergy(AllergyId)
)

CREATE TABLE dbo.UserMealplan
(
	UserMealplanId INT IDENTITY(1,1) PRIMARY KEY,
	ProfileId INT FOREIGN KEY REFERENCES dbo.UserProfile(ProfileId),
	MealplanId INT FOREIGN KEY REFERENCES dbo.Mealplan(MealplanId)
)

CREATE TABLE dbo.Recipe
(
	RecipeId INT IDENTITY(1,1) PRIMARY KEY,
	RecipeName VARCHAR(100) NOT NULL,
	Instructions VARCHAR(MAX),
	PreparationTime INT DEFAULT 0,
	Difficulty CHAR(10) DEFAULT 'EASY',
	RecipeImage VARCHAR(500)
)

CREATE TABLE dbo.RecipeAllergy
(
	RecipeAllergyId INT IDENTITY(1,1) PRIMARY KEY,
	RecipeId INT FOREIGN KEY REFERENCES dbo.Recipe(RecipeId),
	AllergyId INT FOREIGN KEY REFERENCES dbo.Allergy(AllergyId)
)

CREATE TABLE dbo.RecipeMealplan
(
	RecipeMealplanId INT IDENTITY(1,1) PRIMARY KEY,
	RecipeId INT FOREIGN KEY REFERENCES dbo.Recipe(RecipeId),
	MealplanId INT FOREIGN KEY REFERENCES dbo.Mealplan(MealplanId)
)

CREATE TABLE dbo.UserFavourite
(
	UserFavouriteId INT IDENTITY(1,1) PRIMARY KEY,
	ProfileId INT FOREIGN KEY REFERENCES dbo.UserProfile(ProfileId),
	RecipeId INT FOREIGN KEY REFERENCES dbo.Recipe(RecipeId),
)

CREATE TABLE dbo.UserRecipe
(
	UserRecipeId INT IDENTITY(1,1) PRIMARY KEY,
	ProfileId INT FOREIGN KEY REFERENCES dbo.UserProfile(ProfileId),
	RecipeId INT FOREIGN KEY REFERENCES dbo.Recipe(RecipeId),
)