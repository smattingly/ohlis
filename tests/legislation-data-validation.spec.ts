import { test, expect } from "@playwright/test";

test("Given incomplete data, When create is attempted, Then error is shown.", async ({
  page,
}) => {
  const url = "legislation/new";

  // Nothing entered
  await page.goto(url);
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#titleFeedback")).toBeVisible();
  await expect(page.locator("#textFeedback")).toBeVisible();

  // Title only entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "Title:" }).fill("title");
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#titleFeedback")).not.toBeVisible();
  await expect(page.locator("#textFeedback")).toBeVisible();

  // Text only entered.
  await page.goto(url);
  await page.getByRole("textbox", { name: "Text:" }).fill("test");
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("#titleFeedback")).toBeVisible();
  await expect(page.locator("#textFeedback")).not.toBeVisible();
});
