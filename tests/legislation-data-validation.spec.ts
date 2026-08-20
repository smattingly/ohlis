import { test, expect } from "@playwright/test";

test("Given incomplete data, When create is attempted, Then error is shown.", async ({
  page,
}) => {
  const url = "legislation/new";
  // Bootstrap uses the following CSS class to mark form inputs that fail data validation.
  // Test for this rather than visibility of error messages, because some WebKit versions
  // seemed to generate false positives due to weird rendering.
  const invalidDataInput = "is-invalid";

  // Nothing entered
  await page.goto(url);
  await page.locator("#submit").click();
  await expect(page.locator("#title")).toContainClass(invalidDataInput);
  await expect(page.locator("#text")).toContainClass(invalidDataInput);

  // Title only entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "Title:" }).fill("title");
  await page.locator("#submit").click();
  await expect(page.locator("#title")).not.toContainClass(invalidDataInput);
  await expect(page.locator("#text")).toContainClass(invalidDataInput);

  // Text only entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "Text:" }).fill("test");
  await page.locator("#submit").click();
  await expect(page.locator("#title")).toContainClass(invalidDataInput);
  await expect(page.locator("#text")).not.toContainClass(invalidDataInput);
});
