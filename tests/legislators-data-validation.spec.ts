import { test, expect } from "@playwright/test";

test("Given incomplete data, When create is attempted, Then error is shown.", async ({
  page,
}) => {
  const url = "legislators/new";

  // Nothing entered
  await page.goto(url);
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#firstNameFeedback")).toBeVisible();
  await expect(page.locator("#lastNameFeedback")).toBeVisible();
  await expect(page.locator("#hometownFeedback")).toBeVisible();

  // First name only entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "First Name:" }).fill("first");
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#firstNameFeedback")).not.toBeVisible();
  await expect(page.locator("#lastNameFeedback")).toBeVisible();
  await expect(page.locator("#hometownFeedback")).toBeVisible();

  // Last name only entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "Last Name:" }).fill("last");
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#firstNameFeedback")).toBeVisible();
  await expect(page.locator("#lastNameFeedback")).not.toBeVisible();
  await expect(page.locator("#hometownFeedback")).toBeVisible();

  // Hometown only entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "Hometown:" }).fill("hometown");
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#firstNameFeedback")).toBeVisible();
  await expect(page.locator("#lastNameFeedback")).toBeVisible();
  await expect(page.locator("#hometownFeedback")).not.toBeVisible();

  // First name and Last name entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "First Name:" }).fill("first");
  await page.getByRole("textbox", { name: "Last Name:" }).fill("last");
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#firstNameFeedback")).not.toBeVisible();
  await expect(page.locator("#lastNameFeedback")).not.toBeVisible();
  await expect(page.locator("#hometownFeedback")).toBeVisible();

  // First name and Hometown entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "First Name:" }).fill("first");
  await page.getByRole("textbox", { name: "Hometown:" }).fill("hometown");
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#firstNameFeedback")).not.toBeVisible();
  await expect(page.locator("#lastNameFeedback")).toBeVisible();
  await expect(page.locator("#hometownFeedback")).not.toBeVisible();

  // Last name and Hometown entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "Last Name:" }).fill("last");
  await page.getByRole("textbox", { name: "Hometown:" }).fill("hometown");
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#firstNameFeedback")).toBeVisible();
  await expect(page.locator("#lastNameFeedback")).not.toBeVisible();
  await expect(page.locator("#hometownFeedback")).not.toBeVisible();
});
