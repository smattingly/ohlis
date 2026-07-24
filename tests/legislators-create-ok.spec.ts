import { test, expect } from "@playwright/test";

test("Given valid inputs, When new legislator is created, Then it appears in the updated list", async ({
  page,
}) => {
  const newFirstName = "firstname" + crypto.randomUUID();
  const newLastName = "lastname" + crypto.randomUUID();
  const newHometown = "hometown " + crypto.randomUUID();
  await page.goto("http://localhost:8080/legislators/new");
  await page.getByRole("textbox", { name: "First Name:" }).fill(newFirstName);
  await page.getByRole("textbox", { name: "Last Name:" }).fill(newLastName);
  await page.getByRole("textbox", { name: "Hometown:" }).fill(newHometown);
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("tbody")).toContainText(newFirstName);
  await expect(page.locator("tbody")).toContainText(newLastName);
  await expect(page.locator("tbody")).toContainText(newHometown);
});
