import { test, expect } from "@playwright/test";

test("Given incomplete data, When create is attempted, Then error is shown.", async ({
  page,
}) => {
  const url = "legislators/new";

  // Nothing entered
  await page.goto(url);
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#banner-message")).toBeVisible();

  // First name only entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "First Name:" }).fill("first");
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#banner-message")).toBeVisible();

  // Last name only entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "Last Name:" }).fill("last");
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#banner-message")).toBeVisible();

  // Hometown only entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "Hometown:" }).fill("hometown");
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#banner-message")).toBeVisible();

  // First name and Last name entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "First Name:" }).fill("first");
  await page.getByRole("textbox", { name: "Last Name:" }).fill("last");
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#banner-message")).toBeVisible();

  // First name and Hometown entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "First Name:" }).fill("first");
  await page.getByRole("textbox", { name: "Hometown:" }).fill("hometown");
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#banner-message")).toBeVisible();

  // Last name and Hometown entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "Last Name:" }).fill("last");
  await page.getByRole("textbox", { name: "Hometown:" }).fill("hometown");
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#banner-message")).toBeVisible();
});
