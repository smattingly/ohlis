import { test, expect } from "@playwright/test";

test("Given incomplete data, When create is attempted, Then error is shown.", async ({
  page,
}) => {
  const url = "legislators/new";

  // Bootstrap uses the following CSS class to mark form inputs that fail data validation.
  // Test for this rather than visibility of error messages, because some WebKit versions
  // seemed to generate false positives due to weird rendering.
  const invalidDataInput = "is-invalid";

  // Nothing entered
  await page.goto(url);
  await page.locator("#submit").click();
  await expect(page.locator("#firstName")).toContainClass(invalidDataInput);
  await expect(page.locator("#lastName")).toContainClass(invalidDataInput);
  await expect(page.locator("#hometown")).toContainClass(invalidDataInput);

  // First name only entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "First Name:" }).fill("first");
  await page.locator("#submit").click();
  await expect(page.locator("#firstName")).not.toContainClass(invalidDataInput);
  await expect(page.locator("#lastName")).toContainClass(invalidDataInput);
  await expect(page.locator("#hometown")).toContainClass(invalidDataInput);

  // Last name only entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "Last Name:" }).fill("last");
  await page.locator("#submit").click();
  await expect(page.locator("#firstName")).toContainClass(invalidDataInput);
  await expect(page.locator("#lastName")).not.toContainClass(invalidDataInput);
  await expect(page.locator("#hometown")).toContainClass(invalidDataInput);

  // Hometown only entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "Hometown:" }).fill("hometown");
  await page.locator("#submit").click();
  await expect(page.locator("#firstName")).toContainClass(invalidDataInput);
  await expect(page.locator("#lastName")).toContainClass(invalidDataInput);
  await expect(page.locator("#hometown")).not.toContainClass(invalidDataInput);

  // First name and Last name entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "First Name:" }).fill("first");
  await page.getByRole("textbox", { name: "Last Name:" }).fill("last");
  await page.locator("#submit").click();
  await expect(page.locator("#firstName")).not.toContainClass(invalidDataInput);
  await expect(page.locator("#lastName")).not.toContainClass(invalidDataInput);
  await expect(page.locator("#hometown")).toContainClass(invalidDataInput);

  // First name and Hometown entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "First Name:" }).fill("first");
  await page.getByRole("textbox", { name: "Hometown:" }).fill("hometown");
  await page.locator("#submit").click();
  await expect(page.locator("#firstName")).not.toContainClass(invalidDataInput);
  await expect(page.locator("#lastName")).toContainClass(invalidDataInput);
  await expect(page.locator("#hometown")).not.toContainClass(invalidDataInput);

  // Last name and Hometown entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "Last Name:" }).fill("last");
  await page.getByRole("textbox", { name: "Hometown:" }).fill("hometown");
  await page.locator("#submit").click();
  await expect(page.locator("#firstName")).toContainClass(invalidDataInput);
  await expect(page.locator("#lastName")).not.toContainClass(invalidDataInput);
  await expect(page.locator("#hometown")).not.toContainClass(invalidDataInput);
});
