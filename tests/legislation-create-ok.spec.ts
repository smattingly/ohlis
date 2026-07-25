import { test, expect } from "@playwright/test";

test("Given minimal valid inputs, When new legislation is created, Then it appears in the updated list", async ({
  page,
}) => {
  await page.goto("legislation/new");
  const newTitle = "new title " + crypto.randomUUID();
  const newText = "new text " + crypto.randomUUID();
  await page.getByRole("textbox", { name: "Title:" }).fill(newTitle);
  await page.getByRole("textbox", { name: "Text:" }).fill(newText);
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("tbody")).toContainText(newTitle);
  await expect(page.locator("tbody")).toContainText(newText);
});

test("Given expanded valid inputs, When new legislation is created, Then it appears in the updated list", async ({
  page,
}) => {
  await page.goto("legislation/new");
  const newTitle = "new title " + crypto.randomUUID();
  const newText = "new text " + crypto.randomUUID();
  await page.getByRole("textbox", { name: "Title:" }).fill(newTitle);
  await page.getByRole("textbox", { name: "Text:" }).fill(newText);
  await page.getByLabel("Sponsors:").selectOption("9005");
  await page.getByLabel("Sponsors:").selectOption(["9005", "9007"]);
  await page.getByRole("button", { name: "Create" }).click();
  await expect(page.locator("tbody")).toContainText(newTitle);
  await expect(page.locator("tbody")).toContainText(newText);
  await expect(page.locator("tbody")).toContainText("Ed Edwards, Greta Green");
});
